// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for license information.

using AutoRest.Core;
using AutoRest.Core.Utilities;
using AutoRest.Core.Utilities.Collections;
using AutoRest.Extensions;
using AutoRest.Extensions.Azure;
using AutoRest.Java.Model;
using Newtonsoft.Json.Linq;
using Pluralize.NET;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Text.RegularExpressions;
using AutoRestCodeModel = AutoRest.Core.Model.CodeModel;
using AutoRestCompositeType = AutoRest.Core.Model.CompositeType;
using AutoRestDictionaryType = AutoRest.Core.Model.DictionaryType;
using AutoRestEnumType = AutoRest.Core.Model.EnumType;
using AutoRestEnumValue = AutoRest.Core.Model.EnumValue;
using AutoRestHttpMethod = AutoRest.Core.Model.HttpMethod;
using AutoRestIModelType = AutoRest.Core.Model.IModelType;
using AutoRestIParent = AutoRest.Core.Model.IParent;
using AutoRestIVariable = AutoRest.Core.Model.IVariable;
using AutoRestKnownPrimaryType = AutoRest.Core.Model.KnownPrimaryType;
using AutoRestMethod = AutoRest.Core.Model.Method;
using AutoRestMethodGroup = AutoRest.Core.Model.MethodGroup;
using AutoRestModelType = AutoRest.Core.Model.ModelType;
using AutoRestParameter = AutoRest.Core.Model.Parameter;
using AutoRestParameterLocation = AutoRest.Core.Model.ParameterLocation;
using AutoRestParameterMapping = AutoRest.Core.Model.ParameterMapping;
using AutoRestParameterTransformation = AutoRest.Core.Model.ParameterTransformation;
using AutoRestPrimaryType = AutoRest.Core.Model.PrimaryType;
using AutoRestProperty = AutoRest.Core.Model.Property;
using AutoRestResponse = AutoRest.Core.Model.Response;
using AutoRestSequenceType = AutoRest.Core.Model.SequenceType;

namespace AutoRest.Java
{
    internal class ModelParser
    {

        private const string GetByResourceGroup = "GetByResourceGroup";
        private const string ListByResourceGroup = "ListByResourceGroup";
        private const string List = "List";
        private const string ListBySubscription = "ListBySubscription";
        private const string ListAll = "ListAll";
        private const string Delete = "Delete";

        private const string InnerSupportsPrefix = "InnerSupports";
        private const string InnerSupportsGet = InnerSupportsPrefix + "Get";
        private const string InnerSupportsDelete = InnerSupportsPrefix + "Delete";
        private const string InnerSupportsListing = InnerSupportsPrefix + "Listing";

        private static readonly Pluralizer pluralizer = new Pluralizer();
        private static readonly IList<string> addInner = new List<string>();
        private static readonly IList<string> removeInner = new List<string>();

        private static readonly ISet<AutoRestCompositeType> skipParentValidationTypes = new HashSet<AutoRestCompositeType>();
        private static readonly IDictionary<AutoRestIModelType, string> pageImplTypes = new Dictionary<AutoRestIModelType, string>();
        private static readonly ISet<ListType> pagedListTypes = new HashSet<ListType>();

        private static readonly IDictionary<AutoRestIModelType, IModelTypeJv> parsedAutoRestIModelTypes = new Dictionary<AutoRestIModelType, IModelTypeJv>();

        private static readonly Regex methodTypeLeading = new Regex("^/+");
        private static readonly Regex methodTypeTrailing = new Regex("/+$");
        private static readonly Regex enumValueNameRegex = new Regex(@"[\\\/\.\+\ \-]+");

        public JavaCodeNamer Namer { get; private set; }

        public static Client ParseService(AutoRestCodeModel codeModel, JavaSettings settings)
        {
            // List retrieved from
            // http://docs.oracle.com/javase/tutorial/java/nutsandbolts/_keywords.html
            CodeNamer.Instance.ReservedWords.AddRange(new[]
            {
                "abstract", "assert",   "boolean",  "break",    "byte",
                "case",     "catch",    "char",     "class",    "const",
                "continue", "default",  "do",       "double",   "else",
                "enum",     "extends",  "false",    "final",    "finally",
                "float",    "for",      "goto",     "if",       "implements",
                "import",   "int",      "long",     "interface","instanceof",
                "native",   "new",      "null",     "package",  "private",
                "protected","public",   "return",   "short",    "static",
                "strictfp", "super",    "switch",   "synchronized","this",
                "throw",    "throws",   "transient","true",     "try",
                "void",     "volatile", "while"
            });

            if (!settings.IsAzureOrFluent)
            {
                SwaggerExtensions.NormalizeClientModel(codeModel);
            }
            else
            {
                settings.AddCredentials = true;

                // This extension from general extensions must be run prior to Azure specific extensions.
                SwaggerExtensions.ProcessParameterizedHost(codeModel);
                AzureExtensions.ProcessClientRequestIdExtension(codeModel);
                AzureExtensions.UpdateHeadMethods(codeModel);
                SwaggerExtensions.ProcessGlobalParameters(codeModel);
                SwaggerExtensions.FlattenModels(codeModel);
                SwaggerExtensions.FlattenMethodParameters(codeModel);
                ParameterGroupExtensionHelper.AddParameterGroups(codeModel);

                foreach (AutoRestMethodGroup methodGroup in codeModel.Operations)
                {
                    AutoRestMethod[] methods = methodGroup.Methods.ToArray();
                    methodGroup.ClearMethods();
                    foreach (AutoRestMethod method in methods)
                    {
                        methodGroup.Add(method);
                        if (GetExtensionBool(method.Extensions, AzureExtensions.LongRunningExtension))
                        {
                            AutoRestResponse response = method.Responses.Values.First();
                            if (!method.Responses.ContainsKey(HttpStatusCode.OK))
                            {
                                method.Responses[HttpStatusCode.OK] = response;
                            }
                            if (!method.Responses.ContainsKey(HttpStatusCode.Accepted))
                            {
                                method.Responses[HttpStatusCode.Accepted] = response;
                            }
                            if (method.HttpMethod != AutoRestHttpMethod.Get && !method.Responses.ContainsKey(HttpStatusCode.NoContent))
                            {
                                method.Responses[HttpStatusCode.NoContent] = response;
                            }

                            AutoRestMethod m = DependencyInjection.Duplicate(method);
                            var methodName = m.Name.ToPascalCase();
                            method.Name = "begin" + methodName;
                            m.Extensions.Remove(AzureExtensions.LongRunningExtension);
                            methodGroup.Add(m);

                            m = DependencyInjection.Duplicate(method);
                            m.Name = "resume" + methodName;
                            m.Extensions.Add("java-resume", new object());
                            methodGroup.Add(m);
                        }
                    }
                }

                AzureExtensions.AddAzureProperties(codeModel);
                AzureExtensions.SetDefaultResponses(codeModel);

                AzureExtensions.AddPageableMethod(codeModel);

                IDictionary<AutoRestIModelType, AutoRestIModelType> convertedTypes = new Dictionary<AutoRestIModelType, AutoRestIModelType>();
                var pageClasses = new List<PageDetails>();

                foreach (AutoRestMethod restAPIMethod in codeModel.Methods)
                {
                    bool simulateMethodAsPagingOperation = false;
                    AutoRestMethodGroup methodGroup = restAPIMethod.MethodGroup;
                    if (!string.IsNullOrEmpty(methodGroup?.Name?.ToString()))
                    {
                        ServiceMethodType restAPIMethodType = ServiceMethodType.Other;
                        string methodUrl = methodTypeTrailing.Replace(methodTypeLeading.Replace(restAPIMethod.Url, ""), "");
                        string[] urlSplits = methodUrl.Split('/');
                        switch (restAPIMethod.HttpMethod)
                        {
                            case AutoRestHttpMethod.Get:
                                if ((urlSplits.Length == 5 || urlSplits.Length == 7)
                                    && urlSplits[0].EqualsIgnoreCase("subscriptions")
                                    && MethodHasSequenceType(restAPIMethod.ReturnType.Body, settings))
                                {
                                    if (urlSplits.Length == 5)
                                    {
                                        if (urlSplits[2].EqualsIgnoreCase("providers"))
                                        {
                                            restAPIMethodType = ServiceMethodType.ListBySubscription;
                                        }
                                        else
                                        {
                                            restAPIMethodType = ServiceMethodType.ListByResourceGroup;
                                        }
                                    }
                                    else if (urlSplits[2].EqualsIgnoreCase("resourceGroups"))
                                    {
                                        restAPIMethodType = ServiceMethodType.ListByResourceGroup;
                                    }
                                }
                                else if (IsTopLevelResourceUrl(urlSplits))
                                {
                                    restAPIMethodType = ServiceMethodType.Get;
                                }
                                break;

                            case AutoRestHttpMethod.Delete:
                                if (IsTopLevelResourceUrl(urlSplits))
                                {
                                    restAPIMethodType = ServiceMethodType.Delete;
                                }
                                break;
                        }

                        simulateMethodAsPagingOperation = (restAPIMethodType == ServiceMethodType.ListByResourceGroup || restAPIMethodType == ServiceMethodType.ListBySubscription) &&
                            1 == methodGroup.Methods.Count((AutoRestMethod methodGroupMethod) =>
                            {
                                ServiceMethodType methodGroupMethodType = ServiceMethodType.Other;
                                string methodGroupMethodUrl = methodTypeTrailing.Replace(methodTypeLeading.Replace(methodGroupMethod.Url, ""), "");
                                string[] methodGroupUrlSplits = methodGroupMethodUrl.Split('/');
                                switch (methodGroupMethod.HttpMethod)
                                {
                                    case AutoRestHttpMethod.Get:
                                        if ((methodGroupUrlSplits.Length == 5 || methodGroupUrlSplits.Length == 7)
                                        && methodGroupUrlSplits[0].EqualsIgnoreCase("subscriptions")
                                        && MethodHasSequenceType(methodGroupMethod.ReturnType.Body, settings))
                                        {
                                            if (methodGroupUrlSplits.Length == 5)
                                            {
                                                if (methodGroupUrlSplits[2].EqualsIgnoreCase("providers"))
                                                {
                                                    methodGroupMethodType = ServiceMethodType.ListBySubscription;
                                                }
                                                else
                                                {
                                                    methodGroupMethodType = ServiceMethodType.ListByResourceGroup;
                                                }
                                            }
                                            else if (methodGroupUrlSplits[2].EqualsIgnoreCase("resourceGroups"))
                                            {
                                                methodGroupMethodType = ServiceMethodType.ListByResourceGroup;
                                            }
                                        }
                                        else if (IsTopLevelResourceUrl(methodGroupUrlSplits))
                                        {
                                            methodGroupMethodType = ServiceMethodType.Get;
                                        }
                                        break;

                                    case AutoRestHttpMethod.Delete:
                                        if (IsTopLevelResourceUrl(methodGroupUrlSplits))
                                        {
                                            methodGroupMethodType = ServiceMethodType.Delete;
                                        }
                                        break;
                                }
                                return methodGroupMethodType == restAPIMethodType;
                            });
                    }

                    bool methodHasPageableExtensions = restAPIMethod.Extensions.ContainsKey(AzureExtensions.PageableExtension);
                    JContainer methodPageableExtensions = !methodHasPageableExtensions ? null : restAPIMethod.Extensions[AzureExtensions.PageableExtension] as JContainer;
                    if (methodPageableExtensions != null || simulateMethodAsPagingOperation)
                    {
                        string nextLinkName = null;
                        string itemName = "value";
                        string className = null;

                        bool shouldCreatePageDetails = false;

                        if (methodHasPageableExtensions)
                        {
                            if (methodPageableExtensions != null)
                            {
                                shouldCreatePageDetails = true;

                                nextLinkName = (string)methodPageableExtensions["nextLinkName"];
                                itemName = (string)methodPageableExtensions["itemName"] ?? "value";
                                className = (string)methodPageableExtensions["className"];
                            }
                        }
                        else if (simulateMethodAsPagingOperation)
                        {
                            shouldCreatePageDetails = true;
                        }

                        PageDetails pageDetails = null;
                        if (shouldCreatePageDetails)
                        {
                            pageDetails = pageClasses.FirstOrDefault(page => page.NextLinkName == nextLinkName && page.ItemName == itemName);
                            if (pageDetails == null)
                            {
                                if (string.IsNullOrWhiteSpace(className))
                                {
                                    if (pageClasses.Count > 0)
                                    {
                                        className = $"PageImpl{pageClasses.Count}";
                                    }
                                    else
                                    {
                                        className = "PageImpl";
                                    }
                                }

                                pageDetails = new PageDetails(nextLinkName, itemName, className);
                                pageClasses.Add(pageDetails);
                            }

                            if (!string.IsNullOrEmpty(pageDetails.ClassName))
                            {
                                if (string.IsNullOrEmpty(pageDetails.NextLinkName))
                                {
                                    restAPIMethod.Extensions[AzureExtensions.PageableExtension] = null;
                                }

                                bool anyTypeConverted = false;
                                foreach (HttpStatusCode responseStatus in restAPIMethod.Responses.Where(r => r.Value.Body is AutoRestCompositeType).Select(s => s.Key).ToArray())
                                {
                                    anyTypeConverted = true;
                                    AutoRestCompositeType compositeType = (AutoRestCompositeType)restAPIMethod.Responses[responseStatus].Body;
                                    AutoRestSequenceType sequenceType = compositeType.Properties
                                        .Select((AutoRestProperty property) =>
                                        {
                                            AutoRestIModelType propertyModelType = property.ModelType;
                                            if (propertyModelType != null && !property.IsNullable() && propertyModelType is AutoRestPrimaryType propertyModelPrimaryType)
                                            {
                                                AutoRestPrimaryType propertyModelNonNullablePrimaryType = DependencyInjection.New<AutoRestPrimaryType>(propertyModelPrimaryType.KnownPrimaryType);
                                                propertyModelNonNullablePrimaryType.Format = propertyModelPrimaryType.Format;
                                                primaryTypeNotWantNullable.Add(propertyModelNonNullablePrimaryType);

                                                propertyModelType = propertyModelNonNullablePrimaryType;
                                            }
                                            return propertyModelType;
                                        })
                                        .FirstOrDefault(t => t is AutoRestSequenceType) as AutoRestSequenceType;

                                    // if the type is a wrapper over page-able response
                                    if (sequenceType != null)
                                    {
                                        AutoRestSequenceType pagedResult = DependencyInjection.New<AutoRestSequenceType>();
                                        pagedResult.ElementType = sequenceType.ElementType;
                                        SequenceTypeSetPageImplType(pagedResult, pageDetails.ClassName);

                                        convertedTypes[restAPIMethod.Responses[responseStatus].Body] = pagedResult;
                                        AutoRestResponse resp = DependencyInjection.New<AutoRestResponse>(pagedResult, restAPIMethod.Responses[responseStatus].Headers);
                                        restAPIMethod.Responses[responseStatus] = resp;
                                    }
                                }

                                if (!anyTypeConverted && simulateMethodAsPagingOperation)
                                {
                                    foreach (HttpStatusCode responseStatus in restAPIMethod.Responses.Where(r => r.Value.Body is AutoRestSequenceType).Select(s => s.Key).ToArray())
                                    {
                                        AutoRestSequenceType sequenceType = (AutoRestSequenceType)restAPIMethod.Responses[responseStatus].Body;

                                        AutoRestSequenceType pagedResult = DependencyInjection.New<AutoRestSequenceType>();
                                        pagedResult.ElementType = sequenceType.ElementType;
                                        SequenceTypeSetPageImplType(pagedResult, pageDetails.ClassName);

                                        convertedTypes[restAPIMethod.Responses[responseStatus].Body] = pagedResult;
                                        AutoRestResponse resp = DependencyInjection.New<AutoRestResponse>(pagedResult, restAPIMethod.Responses[responseStatus].Headers);
                                        restAPIMethod.Responses[responseStatus] = resp;
                                    }
                                }

                                if (convertedTypes.ContainsKey(restAPIMethod.ReturnType.Body))
                                {
                                    AutoRestResponse resp = DependencyInjection.New<AutoRestResponse>(convertedTypes[restAPIMethod.ReturnType.Body], restAPIMethod.ReturnType.Headers);
                                    restAPIMethod.ReturnType = resp;
                                }
                            }
                        }
                    }
                }

                SwaggerExtensions.RemoveUnreferencedTypes(codeModel,
                    new HashSet<string>(convertedTypes.Keys
                        .Where(x => x is AutoRestCompositeType)
                        .Cast<AutoRestCompositeType>()
                        .Select((AutoRestCompositeType compositeType) =>
                        {
                            string compositeTypeName = compositeType.Name.ToString();
                            if (settings.IsFluent && !string.IsNullOrEmpty(compositeTypeName) && innerModelCompositeType.Contains(compositeType))
                            {
                                compositeTypeName += "Inner";
                            }
                            return compositeTypeName;
                        })));

                if (settings.IsFluent)
                {
                    // determine inner models
                    var included = AutoRest.Core.Settings.Instance.Host?.GetValue<string>("add-inner").Result;
                    if (included != null)
                    {
                        included.Split(',', StringSplitOptions.RemoveEmptyEntries).ForEach(addInner.Add);
                    }
                    var excluded = AutoRest.Core.Settings.Instance.Host?.GetValue<string>("remove-inner").Result;
                    if (excluded != null)
                    {
                        excluded.Split(',', StringSplitOptions.RemoveEmptyEntries).ForEach(removeInner.Add);
                    }

                    foreach (var response in codeModel.Methods
                        .SelectMany(m => m.Responses.Values))
                    {
                        AppendInnerToTopLevelType(response.Body, codeModel, settings);
                    }
                    foreach (var model in codeModel.ModelTypes)
                    {
                        if (addInner.Contains(model.Name))
                        {
                            AppendInnerToTopLevelType(model, codeModel, settings);
                        }
                        else if (codeModel.Operations.Any(o => o.Name.EqualsIgnoreCase(model.Name) || o.Name.EqualsIgnoreCase(pluralizer.Pluralize(model.Name)))) // Naive plural check
                        {
                            AppendInnerToTopLevelType(model, codeModel, settings);
                        }
                    }
                }

                MoveResourceTypeProperties(codeModel);

                // param order (PATH first)
                foreach (AutoRestMethod method in codeModel.Methods)
                {
                    List<AutoRestParameter> parameters = method.Parameters.ToList();
                    method.ClearParameters();
                    foreach (AutoRestParameter parameter in parameters.Where(x => x.Location == AutoRestParameterLocation.Path))
                    {
                        method.Add(parameter);
                    }
                    foreach (AutoRestParameter parameter in parameters.Where(x => x.Location != AutoRestParameterLocation.Path))
                    {
                        method.Add(parameter);
                    }
                }
            }

            string serviceClientName = codeModel.Name;
            string serviceClientDescription = codeModel.Documentation;

            ServiceClient serviceClient = ParseServiceClient(codeModel, settings);

            List<EnumType> enumTypes = new List<EnumType>();
            foreach (AutoRestEnumType autoRestEnumType in codeModel.EnumTypes)
            {
                IModelTypeJv type = ParseEnumType(autoRestEnumType, settings);
                if (type is EnumType enumType)
                {
                    enumTypes.Add(enumType);
                }
            }

            IEnumerable<ClientException> exceptions = ParseExceptions(codeModel, settings);

            IEnumerable<XmlSequenceWrapper> xmlSequenceWrappers = ParseXmlSequenceWrappers(codeModel, settings);

            #region Parse Models
            ClientModels serviceModels = new ClientModels();
            IEnumerable<AutoRestCompositeType> autoRestModelTypes = codeModel.ModelTypes
                .Union(codeModel.HeaderTypes)
                .Where((AutoRestCompositeType autoRestModelType) => ShouldParseModelType(autoRestModelType, settings));
            IEnumerable<ClientModel> models = autoRestModelTypes
                .Select((AutoRestCompositeType autoRestCompositeType) => ParseModel(autoRestCompositeType, settings, serviceModels))
                .ToArray();

            IEnumerable<ResponseJv> responseModels = codeModel.Methods
                .Where(m => m.ReturnType.Headers != null)
                .Select(m => ParseResponse(m, settings))
                .ToList();

            #endregion

            ClientManager manager = ParseManager(serviceClient, codeModel, settings);

            return new Client(serviceClientName, serviceClientDescription, enumTypes, exceptions, xmlSequenceWrappers, responseModels, models, pageClasses, manager, serviceClient);
        }


        private static ClientMethodGroup ParseMethodGroupClient(AutoRestMethodGroup methodGroup, string serviceClientTypeName, JavaSettings settings)
        {
            string interfaceName = methodGroup.Name.ToString().ToPascalCase();
            if (!interfaceName.EndsWith('s'))
            {
                interfaceName += 's';
            }
            interfaceName = AddClientTypePrefix(interfaceName, settings);

            string className = interfaceName;
            if (settings.IsFluent)
            {
                className += "Inner";
            }
            else if (settings.GenerateClientInterfaces)
            {
                className += "Impl";
            }

            string restAPIName = methodGroup.Name.ToString().ToPascalCase();
            if (!restAPIName.EndsWith('s'))
            {
                restAPIName += 's';
            }
            restAPIName += "Service";
            string restAPIBaseURL = methodGroup.CodeModel.BaseUrl;
            List<MethodJv> restAPIMethods = new List<MethodJv>();
            foreach (AutoRestMethod method in methodGroup.Methods)
            {
                restAPIMethods.Add(ParseRestAPIMethod(method, settings));
            }
            NormalizeListMethods(restAPIMethods);
            RestAPI restAPI = new RestAPI(restAPIName, restAPIBaseURL, restAPIMethods);

            List<string> implementedInterfaces = new List<string>();
            if (!settings.IsFluent && settings.GenerateClientInterfaces)
            {
                implementedInterfaces.Add(interfaceName);
            }

            if (settings.IsFluent)
            {
                var getMethod = FindGetMethod(restAPIMethods);
                if (getMethod != null)
                {
                    implementedInterfaces.Add($"{InnerSupportsGet}<{getMethod.ReturnType.AsNullable()}>");
                }

                var deleteMethod = FindDeleteMethod(restAPIMethods);
                if (deleteMethod != null)
                {
                    implementedInterfaces.Add($"{InnerSupportsDelete}<{deleteMethod.ReturnType.AsNullable()}>");
                }

                var listMethod = FindListMethod(restAPIMethods);
                if (listMethod != null)
                {
                    //PageImpl<Element>
                    implementedInterfaces.Add($"{InnerSupportsListing}<{(listMethod.ReturnType as GenericType).TypeArguments.Single()}>");
                }
            }

            string variableType = interfaceName + (settings.IsFluent ? "Inner" : "");
            string variableName = interfaceName.ToCamelCase();

            IEnumerable<ClientMethod> clientMethods = ParseClientMethods(restAPI, settings);

            return new ClientMethodGroup(className, interfaceName, implementedInterfaces, restAPI, serviceClientTypeName, variableType, variableName, clientMethods);
        }

        private static MethodJv ParseRestAPIMethod(AutoRestMethod autoRestMethod, JavaSettings settings)
        {
            string restAPIMethodRequestContentType = autoRestMethod.RequestContentType;

            bool restAPIMethodIsPagingNextOperation = GetExtensionBool(autoRestMethod?.Extensions, "nextLinkMethod");

            string restAPIMethodHttpMethod = autoRestMethod.HttpMethod.ToString().ToUpper();

            string restAPIMethodUrlPath = autoRestMethod.Url.TrimStart('/');

            IEnumerable<HttpStatusCode> restAPIMethodExpectedResponseStatusCodes = autoRestMethod.Responses.Keys.OrderBy(statusCode => statusCode);

            ClassType restAPIMethodExceptionType = null;
            if (autoRestMethod.DefaultResponse.Body != null)
            {
                AutoRestIModelType autoRestExceptionType = autoRestMethod.DefaultResponse.Body;
                IModelTypeJv errorType = ParseType(autoRestExceptionType, settings);

                if (settings.IsAzureOrFluent && (errorType == null || errorType.ToString() == "CloudError"))
                {
                    restAPIMethodExceptionType = ClassType.CloudException;
                }
                else if (errorType is ClassType errorClassType)
                {
                    string exceptionPackage = settings.Package;
                    if (settings.IsFluent)
                    {
                        if (innerModelCompositeType.Contains(autoRestExceptionType))
                        {
                            exceptionPackage = settings.GetSubPackage(settings.ImplementationSubpackage);
                        }
                    }
                    else
                    {
                        exceptionPackage = settings.GetSubPackage(settings.ModelsSubpackage);
                    }

                    string exceptionName = errorClassType.GetExtensionValue(SwaggerExtensions.NameOverrideExtension);
                    if (string.IsNullOrEmpty(exceptionName))
                    {
                        exceptionName = errorClassType.Name;
                        if (settings.IsFluent && !string.IsNullOrEmpty(exceptionName) && errorClassType.IsInnerModelType)
                        {
                            exceptionName += "Inner";
                        }
                        exceptionName += "Exception";
                    }
                    restAPIMethodExceptionType = new ClassType(exceptionPackage, exceptionName, null, null, false);
                }
                else
                {
                    restAPIMethodExceptionType = ClassType.RestException;
                }
            }

            string wellKnownMethodName = null;
            AutoRestMethodGroup methodGroup = autoRestMethod.MethodGroup;
            if (!string.IsNullOrEmpty(methodGroup?.Name?.ToString()))
            {
                ServiceMethodType methodType = ServiceMethodType.Other;
                string methodUrl = methodTypeTrailing.Replace(methodTypeLeading.Replace(autoRestMethod.Url, ""), "");
                string[] methodUrlSplits = methodUrl.Split('/');
                switch (autoRestMethod.HttpMethod)
                {
                    case AutoRestHttpMethod.Get:
                        if ((methodUrlSplits.Length == 5 || methodUrlSplits.Length == 7)
                            && methodUrlSplits[0].EqualsIgnoreCase("subscriptions")
                            && MethodHasSequenceType(autoRestMethod.ReturnType.Body, settings))
                        {
                            if (methodUrlSplits.Length == 5)
                            {
                                if (methodUrlSplits[2].EqualsIgnoreCase("providers"))
                                {
                                    methodType = ServiceMethodType.ListBySubscription;
                                }
                                else
                                {
                                    methodType = ServiceMethodType.ListByResourceGroup;
                                }
                            }
                            else if (methodUrlSplits[2].EqualsIgnoreCase("resourceGroups"))
                            {
                                methodType = ServiceMethodType.ListByResourceGroup;
                            }
                        }
                        else if (IsTopLevelResourceUrl(methodUrlSplits))
                        {
                            methodType = ServiceMethodType.Get;
                        }
                        break;

                    case AutoRestHttpMethod.Delete:
                        if (IsTopLevelResourceUrl(methodUrlSplits))
                        {
                            methodType = ServiceMethodType.Delete;
                        }
                        break;
                }

                if (methodType != ServiceMethodType.Other)
                {
                    int methodsWithSameType = methodGroup.Methods.Count((AutoRestMethod methodGroupMethod) =>
                    {
                        ServiceMethodType methodGroupMethodType = ServiceMethodType.Other;
                        string methodGroupMethodUrl = methodTypeTrailing.Replace(methodTypeLeading.Replace(methodGroupMethod.Url, ""), "");
                        string[] methodGroupMethodUrlSplits = methodGroupMethodUrl.Split('/');
                        switch (methodGroupMethod.HttpMethod)
                        {
                            case AutoRestHttpMethod.Get:
                                if ((methodGroupMethodUrlSplits.Length == 5 || methodGroupMethodUrlSplits.Length == 7)
                                    && methodGroupMethodUrlSplits[0].EqualsIgnoreCase("subscriptions")
                                    && MethodHasSequenceType(methodGroupMethod.ReturnType.Body, settings))
                                {
                                    if (methodGroupMethodUrlSplits.Length == 5)
                                    {
                                        if (methodGroupMethodUrlSplits[2].EqualsIgnoreCase("providers"))
                                        {
                                            methodGroupMethodType = ServiceMethodType.ListBySubscription;
                                        }
                                        else
                                        {
                                            methodGroupMethodType = ServiceMethodType.ListByResourceGroup;
                                        }
                                    }
                                    else if (methodGroupMethodUrlSplits[2].EqualsIgnoreCase("resourceGroups"))
                                    {
                                        methodGroupMethodType = ServiceMethodType.ListByResourceGroup;
                                    }
                                }
                                else if (IsTopLevelResourceUrl(methodGroupMethodUrlSplits))
                                {
                                    methodGroupMethodType = ServiceMethodType.Get;
                                }
                                break;

                            case AutoRestHttpMethod.Delete:
                                if (IsTopLevelResourceUrl(methodGroupMethodUrlSplits))
                                {
                                    methodGroupMethodType = ServiceMethodType.Delete;
                                }
                                break;
                        }
                        return methodGroupMethodType == methodType;
                    });

                    if (methodsWithSameType == 1)
                    {
                        switch (methodType)
                        {
                            case ServiceMethodType.ListBySubscription:
                                wellKnownMethodName = List;
                                break;

                            case ServiceMethodType.ListByResourceGroup:
                                wellKnownMethodName = ListByResourceGroup;
                                break;

                            case ServiceMethodType.Delete:
                                wellKnownMethodName = Delete;
                                break;

                            case ServiceMethodType.Get:
                                wellKnownMethodName = GetByResourceGroup;
                                break;

                            default:
                                throw new Exception("Flow should not hit this statement.");
                        }
                    }
                }
            }
            string restAPIMethodName;
            if (!string.IsNullOrWhiteSpace(wellKnownMethodName))
            {
                AutoRestIParent methodParent = autoRestMethod.Parent;
                restAPIMethodName = CodeNamer.Instance.GetUnique(wellKnownMethodName, autoRestMethod, methodParent.IdentifiersInScope, methodParent.Children.Except(autoRestMethod.SingleItemAsEnumerable()));
            }
            else
            {
                restAPIMethodName = autoRestMethod.Name;
            }
            restAPIMethodName = restAPIMethodName.ToCamelCase();

            bool restAPIMethodSimulateMethodAsPagingOperation = (wellKnownMethodName == List || wellKnownMethodName == ListByResourceGroup);

            bool restAPIMethodIsLongRunningOperation = GetExtensionBool(autoRestMethod?.Extensions, AzureExtensions.LongRunningExtension);

            AutoRestResponse autoRestRestAPIMethodReturnType = autoRestMethod.ReturnType;
            IModelTypeJv responseBodyType = ParseType(autoRestRestAPIMethodReturnType.Body, settings);
            ListType responseBodyWireListType = responseBodyType as ListType;

            AutoRestIModelType autorestRestAPIMethodReturnClientType = ConvertToClientType(autoRestRestAPIMethodReturnType.Body ?? DependencyInjection.New<AutoRestPrimaryType>(AutoRestKnownPrimaryType.None));
            AutoRestSequenceType autorestRestAPIMethodReturnClientSequenceType = autorestRestAPIMethodReturnClientType as AutoRestSequenceType;

            bool autorestRestAPIMethodReturnTypeIsPaged = GetExtensionBool(autoRestMethod.Extensions, "nextLinkMethod") ||
                (autoRestMethod.Extensions.ContainsKey(AzureExtensions.PageableExtension) &&
                 autoRestMethod.Extensions[AzureExtensions.PageableExtension] != null);

            if (settings.IsAzureOrFluent && responseBodyWireListType != null && (autorestRestAPIMethodReturnTypeIsPaged || restAPIMethodSimulateMethodAsPagingOperation))
            {
                AutoRestSequenceType autoRestRestAPIMethodReturnClientPageListType = DependencyInjection.New<AutoRestSequenceType>();
                autoRestRestAPIMethodReturnClientPageListType.ElementType = autorestRestAPIMethodReturnClientSequenceType.ElementType;

                string pageContainerSubPackage = (settings.IsFluent ? settings.ImplementationSubpackage : settings.ModelsSubpackage);
                string pageContainerPackage = $"{settings.Package}.{pageContainerSubPackage}";
                string pageContainerTypeName = SequenceTypeGetPageImplType(autorestRestAPIMethodReturnClientSequenceType);

                SequenceTypeSetPageImplType(autoRestRestAPIMethodReturnClientPageListType, pageContainerTypeName);
                autoRestPagedListTypes.Add(autoRestRestAPIMethodReturnClientPageListType);

                responseBodyType = new GenericType(pageContainerPackage, pageContainerTypeName, responseBodyWireListType.ElementType);
                pagedListTypes.Add(responseBodyWireListType);
            }

            // If there is a stream body and no Content-Length header parameter, add one automatically
            // Convert to list so we can use methods like FindIndex and Insert(int, T)
            List<AutoRestParameter> autoRestMethodParameters = new List<AutoRestParameter>(autoRestMethod.Parameters);
            int streamBodyParameterIndex = autoRestMethodParameters.FindIndex(p => p.Location == AutoRestParameterLocation.Body && p.ModelType is AutoRestPrimaryType mt && mt.KnownPrimaryType == AutoRestKnownPrimaryType.Stream);
            if (streamBodyParameterIndex != -1 &&
                !autoRestMethodParameters.Any(p =>
                    p.Location == AutoRestParameterLocation.Header && p.SerializedName.EqualsIgnoreCase("Content-Length")))
            {
                AutoRestParameter contentLengthParameter = DependencyInjection.New<AutoRestParameter>();
                contentLengthParameter.Method = autoRestMethod;
                contentLengthParameter.IsRequired = true;
                contentLengthParameter.Location = AutoRestParameterLocation.Header;
                contentLengthParameter.SerializedName = "Content-Length";
                contentLengthParameter.Name = "contentLength";
                contentLengthParameter.Documentation = "The content length";
                contentLengthParameter.ModelType = DependencyInjection.New<AutoRestPrimaryType>(AutoRestKnownPrimaryType.Long);

                // Add the Content-Length parameter before the body parameter
                autoRestMethodParameters.Insert(streamBodyParameterIndex, contentLengthParameter);
                autoRestMethod.ClearParameters();
                autoRestMethod.AddRange(autoRestMethodParameters);
            }

            IModelTypeJv restAPIMethodReturnType;
            IModelTypeJv restAPIMethodReturnTypeAsync;
            if (restAPIMethodIsLongRunningOperation)
            {
                IModelTypeJv operationStatusTypeArgument;
                if (settings.IsAzureOrFluent && responseBodyWireListType != null && (autorestRestAPIMethodReturnTypeIsPaged || restAPIMethodSimulateMethodAsPagingOperation))
                {
                    operationStatusTypeArgument = GenericType.Page(responseBodyWireListType.ElementType);
                    restAPIMethodReturnType = GenericType.PagedList(responseBodyWireListType.ElementType);
                }
                else
                {
                    operationStatusTypeArgument = responseBodyType;
                    restAPIMethodReturnType = responseBodyType;
                }
                restAPIMethodReturnTypeAsync = GenericType.Observable(GenericType.OperationStatus(operationStatusTypeArgument));
            }
            else
            {
                IModelTypeJv singleValueType;
                if (autoRestRestAPIMethodReturnType.Headers != null)
                {
                    string className = autoRestMethod.MethodGroup.Name.ToPascalCase() + autoRestMethod.Name.ToPascalCase() + "Response";
                    singleValueType = new ClassType(settings.Package + "." + settings.ModelsSubpackage, className);
                    restAPIMethodReturnType = singleValueType;
                }
                else if (responseBodyType.Equals(GenericType.FlowableByteBuffer))
                {
                    singleValueType = ClassType.StreamResponse;
                    restAPIMethodReturnType = responseBodyType;
                }
                else if (responseBodyType.Equals(PrimitiveType.Void))
                {
                    singleValueType = ClassType.VoidResponse;
                    restAPIMethodReturnType = responseBodyType;
                }
                else
                {
                    singleValueType = GenericType.BodyResponse(responseBodyType);
                    restAPIMethodReturnType = responseBodyType;
                }
                restAPIMethodReturnTypeAsync = GenericType.Single(singleValueType);
            }

            List<ParameterJv> restAPIMethodParameters = new List<ParameterJv>();
            bool isResumable = autoRestMethod.Extensions.ContainsKey("java-resume");
            if (isResumable)
            {
                restAPIMethodParameters.Add(new ParameterJv(
                    description: "The OperationDescription object.",
                    autoRestParameter: null,
                    type: ClassType.OperationDescription,
                    name: "operationDescription",
                    requestParameterLocation: RequestParameterLocation.None,
                    requestParameterName: "operationDescription",
                    alreadyEncoded: true,
                    isConstant: false,
                    isRequired: true,
                    isServiceClientProperty: false,
                    headerCollectionPrefix: null));
            }
            else
            {
                List<AutoRestParameter> autoRestRestAPIMethodParameters = autoRestMethod.LogicalParameters.Where(p => p.Location != AutoRestParameterLocation.None).ToList();

                List<AutoRestParameter> autoRestMethodLogicalParameters = autoRestMethod.LogicalParameters.Where(p => p.Location != AutoRestParameterLocation.None).ToList();

                if (settings.IsAzureOrFluent && restAPIMethodIsPagingNextOperation)
                {
                    var pathParameters = autoRestMethodLogicalParameters.Where(p => p.Location == AutoRestParameterLocation.Path);
                    var nextUrlParameter = pathParameters.Count() == 1 ? pathParameters.First() : null;

                    restAPIMethodParameters.Add(new ParameterJv(
                        description: "The URL to get the next page of items.",
                        autoRestParameter: nextUrlParameter,
                        type: ClassType.String,
                        name: "nextUrl",
                        requestParameterLocation: RequestParameterLocation.Path,
                        requestParameterName: "nextUrl",
                        alreadyEncoded: true,
                        isConstant: false,
                        isRequired: true,
                        isServiceClientProperty: false,
                        headerCollectionPrefix: null));

                    autoRestMethodLogicalParameters.RemoveAll(p => p.Location == AutoRestParameterLocation.Path);
                }

                IEnumerable<AutoRestParameter> autoRestRestAPIMethodOrderedParameters = autoRestMethodLogicalParameters
                    .Where(p => p.Location == AutoRestParameterLocation.Path)
                    .Union(autoRestMethodLogicalParameters.Where(p => p.Location != AutoRestParameterLocation.Path));

                foreach (AutoRestParameter autoRestParameter in autoRestRestAPIMethodOrderedParameters)
                {
                    string parameterRequestName = autoRestParameter.SerializedName;

                    RequestParameterLocation parameterRequestLocation = ParseParameterRequestLocation(autoRestParameter.Location);
                    if (autoRestMethod.Url.Contains("{" + parameterRequestName + "}"))
                    {
                        parameterRequestLocation = RequestParameterLocation.Path;
                    }
                    else if (autoRestParameter.Extensions.ContainsKey("hostParameter"))
                    {
                        parameterRequestLocation = RequestParameterLocation.Host;
                    }

                    string parameterHeaderCollectionPrefix = GetExtensionString(autoRestParameter.Extensions, SwaggerExtensions.HeaderCollectionPrefix);

                    AutoRestIModelType autoRestParameterWireType = autoRestParameter.ModelType;
                    IModelTypeJv parameterType = ParseType(autoRestParameter, settings);
                    if (parameterType is ListType && settings.ShouldGenerateXmlSerialization && parameterRequestLocation == RequestParameterLocation.Body)
                    {
                        string parameterTypePackage = settings.GetSubPackage(settings.ImplementationSubpackage);
                        string parameterTypeName = autoRestParameterWireType.XmlName.ToPascalCase() + "Wrapper";
                        parameterType = new ClassType(parameterTypePackage, parameterTypeName, null, null, false);
                    }
                    else if (parameterType == ArrayType.ByteArray)
                    {
                        if (parameterRequestLocation != RequestParameterLocation.Body && parameterRequestLocation != RequestParameterLocation.FormData)
                        {
                            parameterType = ClassType.String;
                        }
                    }
                    else if (parameterType is ListType && autoRestParameter.Location != AutoRestParameterLocation.Body && autoRestParameter.Location != AutoRestParameterLocation.FormData)
                    {
                        parameterType = ClassType.String;
                    }

                    bool parameterIsNullable = autoRestParameter.IsNullable();
                    if (parameterIsNullable)
                    {
                        parameterType = parameterType.AsNullable();
                    }

                    string parameterDescription = autoRestParameter.Documentation;
                    if (string.IsNullOrEmpty(parameterDescription))
                    {
                        parameterDescription = $"the {parameterType} value";
                    }

                    string parameterVariableName = autoRestParameter.ClientProperty?.Name?.ToString();
                    if (!string.IsNullOrEmpty(parameterVariableName))
                    {
                        CodeNamer codeNamer = CodeNamer.Instance;
                        parameterVariableName = codeNamer.CamelCase(codeNamer.RemoveInvalidCharacters(parameterVariableName));
                    }
                    if (parameterVariableName == null)
                    {
                        if (!autoRestParameter.IsClientProperty)
                        {
                            parameterVariableName = autoRestParameter.Name;
                        }
                        else
                        {
                            string caller = (autoRestParameter.Method != null && autoRestParameter.Method.Group.IsNullOrEmpty() ? "this" : "this.client");
                            string clientPropertyName = autoRestParameter.ClientProperty?.Name?.ToString();
                            if (!string.IsNullOrEmpty(clientPropertyName))
                            {
                                CodeNamer codeNamer = CodeNamer.Instance;
                                clientPropertyName = codeNamer.CamelCase(codeNamer.RemoveInvalidCharacters(clientPropertyName));
                            }
                            parameterVariableName = $"{caller}.{clientPropertyName}()";
                        }
                    }

                    bool parameterSkipUrlEncodingExtension = GetExtensionBool(autoRestParameter.Extensions, SwaggerExtensions.SkipUrlEncodingExtension);

                    bool parameterIsConstant = autoRestParameter.IsConstant;

                    bool parameterIsRequired = autoRestParameter.IsRequired;

                    bool parameterIsServiceClientProperty = autoRestParameter.IsClientProperty;

                    restAPIMethodParameters.Add(new ParameterJv(parameterDescription, autoRestParameter, parameterType, parameterVariableName, parameterRequestLocation, parameterRequestName, parameterSkipUrlEncodingExtension, parameterIsConstant, parameterIsRequired, parameterIsServiceClientProperty, parameterHeaderCollectionPrefix));
                }
            }

            string restAPIMethodDescription = "";
            if (!string.IsNullOrEmpty(autoRestMethod.Summary))
            {
                restAPIMethodDescription += autoRestMethod.Summary;
            }
            if (!string.IsNullOrEmpty(autoRestMethod.Description))
            {
                if (restAPIMethodDescription != "")
                {
                    restAPIMethodDescription += Environment.NewLine;
                }
                restAPIMethodDescription += autoRestMethod.Description;
            }

            bool restAPIMethodIsPagingOperation = autoRestMethod.Extensions.ContainsKey(AzureExtensions.PageableExtension) &&
                autoRestMethod.Extensions[AzureExtensions.PageableExtension] != null &&
                !restAPIMethodIsPagingNextOperation;

            IModelTypeJv restAPIMethodReturnValueWireType = returnValueWireTypeOptions.FirstOrDefault((IModelTypeJv type) => restAPIMethodReturnType.Contains(type));
            if (unixTimeTypes.Contains(restAPIMethodReturnValueWireType))
            {
                restAPIMethodReturnValueWireType = ClassType.UnixTime;
            }

            AutoRestMethodGroup autoRestMethodGroup = autoRestMethod.MethodGroup;
            ServiceMethodType autoRestRestAPIMethodType = ServiceMethodType.Other;
            if (!string.IsNullOrEmpty(autoRestMethodGroup?.Name?.ToString()))
            {
                string autoRestMethodUrl = methodTypeTrailing.Replace(methodTypeLeading.Replace(restAPIMethodUrlPath, ""), "");
                string[] autoRestMethodUrlSplits = autoRestMethodUrl.Split('/');
                switch (autoRestMethod.HttpMethod)
                {
                    case AutoRestHttpMethod.Get:
                        if ((autoRestMethodUrlSplits.Length == 5 || autoRestMethodUrlSplits.Length == 7)
                            && autoRestMethodUrlSplits[0].EqualsIgnoreCase("subscriptions")
                            && MethodHasSequenceType(autoRestMethod.ReturnType.Body, settings))
                        {
                            if (autoRestMethodUrlSplits.Length == 5)
                            {
                                if (autoRestMethodUrlSplits[2].EqualsIgnoreCase("providers"))
                                {
                                    autoRestRestAPIMethodType = ServiceMethodType.ListBySubscription;
                                }
                                else
                                {
                                    autoRestRestAPIMethodType = ServiceMethodType.ListByResourceGroup;
                                }
                            }
                            else if (autoRestMethodUrlSplits[2].EqualsIgnoreCase("resourceGroups"))
                            {
                                autoRestRestAPIMethodType = ServiceMethodType.ListByResourceGroup;
                            }
                        }
                        else if (IsTopLevelResourceUrl(autoRestMethodUrlSplits))
                        {
                            autoRestRestAPIMethodType = ServiceMethodType.Get;
                        }
                        break;

                    case AutoRestHttpMethod.Delete:
                        if (IsTopLevelResourceUrl(autoRestMethodUrlSplits))
                        {
                            autoRestRestAPIMethodType = ServiceMethodType.Delete;
                        }
                        break;
                }
            }

            return new MethodJv(
                restAPIMethodRequestContentType,
                restAPIMethodReturnType,
                restAPIMethodIsPagingNextOperation,
                restAPIMethodHttpMethod,
                restAPIMethodUrlPath,
                restAPIMethodExpectedResponseStatusCodes,
                restAPIMethodExceptionType,
                restAPIMethodName,
                restAPIMethodReturnTypeAsync,
                restAPIMethodParameters,
                restAPIMethodIsPagingOperation,
                restAPIMethodDescription,
                restAPIMethodSimulateMethodAsPagingOperation,
                restAPIMethodIsLongRunningOperation,
                restAPIMethodReturnValueWireType,
                autoRestMethod,
                isResumable,
                autoRestRestAPIMethodType);
        }

        private static PagingNextMethodInfo GetPagingNextMethodInfo(MethodJv restAPIMethod, ClientMethodType clientMethodType, IEnumerable<ClientParameter> clientParameters, bool onlyRequiredParameters, JavaSettings settings)
        {
            AutoRestResponse autoRestRestAPIMethodReturnType = restAPIMethod.AutoRestMethod.ReturnType;
            AutoRestIModelType autoRestRestAPIMethodReturnBodyType = autoRestRestAPIMethodReturnType.Body ?? DependencyInjection.New<AutoRestPrimaryType>(AutoRestKnownPrimaryType.None);

            IModelTypeJv restAPIMethodReturnBodyClientType = ConvertToClientType(ModelParser.ParseType(autoRestRestAPIMethodReturnBodyType, settings));

            IModelTypeJv pageType;

            if (settings.IsAzureOrFluent &&
                restAPIMethodReturnBodyClientType is ListType restAPIMethodReturnBodyClientListType &&
                (restAPIMethod.IsPagingOperation || restAPIMethod.IsPagingNextOperation || restAPIMethod.SimulateAsPagingOperation))
            {
                IModelTypeJv restAPIMethodReturnBodyClientListElementType = restAPIMethodReturnBodyClientListType.ElementType;

                restAPIMethodReturnBodyClientType = GenericType.PagedList(restAPIMethodReturnBodyClientListElementType);

                string pageImplSubPackage = settings.IsFluent ? settings.ImplementationSubpackage : settings.ModelsSubpackage;
                string pageImplPackage = $"{settings.Package}.{pageImplSubPackage}";

                pageType = GenericType.Page(restAPIMethodReturnBodyClientListElementType);
            }
            else
            {
                pageType = restAPIMethodReturnBodyClientType.AsNullable();
            }

            ClientParameter serviceCallbackParameter = new ClientParameter(
                description: "the async ServiceCallback to handle successful and failed responses.",
                isFinal: false,
                type: GenericType.ServiceCallback(restAPIMethodReturnBodyClientType),
                name: "serviceCallback",
                isRequired: true,
                // GetClientMethodParameterAnnotations() is provided false for isRequired so
                // that this parameter won't get marked as NonNull.
                annotations: settings.GetClientMethodParameterAnnotations(false));

            AutoRestMethod nextMethod = null;
            string nextMethodInvocation = null;
            if (restAPIMethod.IsPagingNextOperation)
            {
                nextMethod = restAPIMethod.AutoRestMethod;

                nextMethodInvocation = restAPIMethod.Name;
                string nextMethodWellKnownMethodName = null;
                if (!string.IsNullOrEmpty(restAPIMethod.AutoRestMethod.MethodGroup?.Name?.ToString()))
                {
                    if (restAPIMethod.ServiceMethodType != ServiceMethodType.Other)
                    {
                        int methodsWithSameType = restAPIMethod.AutoRestMethod.MethodGroup.Methods.Count((AutoRestMethod methodGroupMethod) =>
                        {
                            ServiceMethodType methodGroupMethodType = ServiceMethodType.Other;
                            string methodGroupMethodUrl = methodTypeTrailing.Replace(methodTypeLeading.Replace(methodGroupMethod.Url, ""), "");
                            string[] methodGroupMethodUrlSplits = methodGroupMethodUrl.Split('/');
                            switch (methodGroupMethod.HttpMethod)
                            {
                                case AutoRestHttpMethod.Get:
                                    if ((methodGroupMethodUrlSplits.Length == 5 || methodGroupMethodUrlSplits.Length == 7)
                                        && methodGroupMethodUrlSplits[0].EqualsIgnoreCase("subscriptions")
                                        && MethodHasSequenceType(methodGroupMethod.ReturnType.Body, settings))
                                    {
                                        if (methodGroupMethodUrlSplits.Length == 5)
                                        {
                                            if (methodGroupMethodUrlSplits[2].EqualsIgnoreCase("providers"))
                                            {
                                                methodGroupMethodType = ServiceMethodType.ListBySubscription;
                                            }
                                            else
                                            {
                                                methodGroupMethodType = ServiceMethodType.ListByResourceGroup;
                                            }
                                        }
                                        else if (methodGroupMethodUrlSplits[2].EqualsIgnoreCase("resourceGroups"))
                                        {
                                            methodGroupMethodType = ServiceMethodType.ListByResourceGroup;
                                        }
                                    }
                                    else if (IsTopLevelResourceUrl(methodGroupMethodUrlSplits))
                                    {
                                        methodGroupMethodType = ServiceMethodType.Get;
                                    }
                                    break;

                                case AutoRestHttpMethod.Delete:
                                    if (IsTopLevelResourceUrl(methodGroupMethodUrlSplits))
                                    {
                                        methodGroupMethodType = ServiceMethodType.Delete;
                                    }
                                    break;
                            }
                            return methodGroupMethodType == restAPIMethod.ServiceMethodType;
                        });

                        if (methodsWithSameType == 1)
                        {
                            switch (restAPIMethod.ServiceMethodType)
                            {
                                case ServiceMethodType.ListBySubscription:
                                    nextMethodWellKnownMethodName = List;
                                    break;

                                case ServiceMethodType.ListByResourceGroup:
                                    nextMethodWellKnownMethodName = ListByResourceGroup;
                                    break;

                                case ServiceMethodType.Delete:
                                    nextMethodWellKnownMethodName = Delete;
                                    break;

                                case ServiceMethodType.Get:
                                    nextMethodWellKnownMethodName = GetByResourceGroup;
                                    break;

                                default:
                                    throw new Exception("Flow should not hit this statement.");
                            }
                        }
                    }
                }
                if (!string.IsNullOrWhiteSpace(nextMethodWellKnownMethodName))
                {
                    AutoRestIParent methodParent = restAPIMethod.AutoRestMethod.Parent;
                    nextMethodInvocation = CodeNamer.Instance.GetUnique(nextMethodWellKnownMethodName, restAPIMethod.AutoRestMethod, methodParent.IdentifiersInScope, methodParent.Children.Except(restAPIMethod.AutoRestMethod.SingleItemAsEnumerable()));
                }
                nextMethodInvocation = nextMethodInvocation.ToCamelCase();
            }
            else if (restAPIMethod.IsPagingOperation)
            {
                string nextMethodName = restAPIMethod.AutoRestMethod.Extensions?.GetValue<Fixable<string>>("nextMethodName")?.ToCamelCase();
                string nextMethodGroup = restAPIMethod.AutoRestMethod.Extensions?.GetValue<Fixable<string>>("nextMethodGroup")?.Value;

                nextMethod = restAPIMethod.AutoRestMethod.CodeModel.Methods
                    .FirstOrDefault((AutoRestMethod codeModelMethod) =>
                    {
                        bool result = nextMethodGroup.EqualsIgnoreCase(codeModelMethod.Group);
                        if (result)
                        {
                            string codeModelMethodName = codeModelMethod.Name;
                            string codeModelMethodWellKnownMethodName = null;
                            AutoRestMethodGroup codeModelMethodMethodGroup = codeModelMethod.MethodGroup;
                            if (!string.IsNullOrEmpty(codeModelMethodMethodGroup?.Name?.ToString()))
                            {
                                ServiceMethodType codeModelMethodType = ServiceMethodType.Other;
                                string codeModelMethodUrl = methodTypeTrailing.Replace(methodTypeLeading.Replace(codeModelMethod.Url, ""), "");
                                string[] codeModelMethodUrlSplits = codeModelMethodUrl.Split('/');
                                switch (codeModelMethod.HttpMethod)
                                {
                                    case AutoRestHttpMethod.Get:
                                        if ((codeModelMethodUrlSplits.Length == 5 || codeModelMethodUrlSplits.Length == 7)
                                                        && codeModelMethodUrlSplits[0].EqualsIgnoreCase("subscriptions")
                                                        && MethodHasSequenceType(codeModelMethod.ReturnType.Body, settings))
                                        {
                                            if (codeModelMethodUrlSplits.Length == 5)
                                            {
                                                if (codeModelMethodUrlSplits[2].EqualsIgnoreCase("providers"))
                                                {
                                                    codeModelMethodType = ServiceMethodType.ListBySubscription;
                                                }
                                                else
                                                {
                                                    codeModelMethodType = ServiceMethodType.ListByResourceGroup;
                                                }
                                            }
                                            else if (codeModelMethodUrlSplits[2].EqualsIgnoreCase("resourceGroups"))
                                            {
                                                codeModelMethodType = ServiceMethodType.ListByResourceGroup;
                                            }
                                        }
                                        else if (IsTopLevelResourceUrl(codeModelMethodUrlSplits))
                                        {
                                            codeModelMethodType = ServiceMethodType.Get;
                                        }
                                        break;

                                    case AutoRestHttpMethod.Delete:
                                        if (IsTopLevelResourceUrl(codeModelMethodUrlSplits))
                                        {
                                            codeModelMethodType = ServiceMethodType.Delete;
                                        }
                                        break;
                                }

                                if (codeModelMethodType != ServiceMethodType.Other)
                                {
                                    int methodsWithSameType = codeModelMethodMethodGroup.Methods.Count((AutoRestMethod methodGroupMethod) =>
                                    {
                                        ServiceMethodType methodGroupMethodType = ServiceMethodType.Other;
                                        string methodGroupMethodUrl = methodTypeTrailing.Replace(methodTypeLeading.Replace(methodGroupMethod.Url, ""), "");
                                        string[] methodGroupMethodUrlSplits = methodGroupMethodUrl.Split('/');
                                        switch (methodGroupMethod.HttpMethod)
                                        {
                                            case AutoRestHttpMethod.Get:
                                                if ((methodGroupMethodUrlSplits.Length == 5 || methodGroupMethodUrlSplits.Length == 7)
                                                                && methodGroupMethodUrlSplits[0].EqualsIgnoreCase("subscriptions")
                                                                && MethodHasSequenceType(methodGroupMethod.ReturnType.Body, settings))
                                                {
                                                    if (methodGroupMethodUrlSplits.Length == 5)
                                                    {
                                                        if (methodGroupMethodUrlSplits[2].EqualsIgnoreCase("providers"))
                                                        {
                                                            methodGroupMethodType = ServiceMethodType.ListBySubscription;
                                                        }
                                                        else
                                                        {
                                                            methodGroupMethodType = ServiceMethodType.ListByResourceGroup;
                                                        }
                                                    }
                                                    else if (methodGroupMethodUrlSplits[2].EqualsIgnoreCase("resourceGroups"))
                                                    {
                                                        methodGroupMethodType = ServiceMethodType.ListByResourceGroup;
                                                    }
                                                }
                                                else if (IsTopLevelResourceUrl(methodGroupMethodUrlSplits))
                                                {
                                                    methodGroupMethodType = ServiceMethodType.Get;
                                                }
                                                break;

                                            case AutoRestHttpMethod.Delete:
                                                if (IsTopLevelResourceUrl(methodGroupMethodUrlSplits))
                                                {
                                                    methodGroupMethodType = ServiceMethodType.Delete;
                                                }
                                                break;
                                        }
                                        return methodGroupMethodType == restAPIMethod.ServiceMethodType;
                                    });

                                    if (methodsWithSameType == 1)
                                    {
                                        switch (codeModelMethodType)
                                        {
                                            case ServiceMethodType.ListBySubscription:
                                                codeModelMethodWellKnownMethodName = List;
                                                break;

                                            case ServiceMethodType.ListByResourceGroup:
                                                codeModelMethodWellKnownMethodName = ListByResourceGroup;
                                                break;

                                            case ServiceMethodType.Delete:
                                                codeModelMethodWellKnownMethodName = Delete;
                                                break;

                                            case ServiceMethodType.Get:
                                                codeModelMethodWellKnownMethodName = GetByResourceGroup;
                                                break;

                                            default:
                                                throw new Exception("Flow should not hit this statement.");
                                        }
                                    }
                                }
                            }
                            if (!string.IsNullOrWhiteSpace(codeModelMethodWellKnownMethodName))
                            {
                                AutoRestIParent methodParent = codeModelMethod.Parent;
                                codeModelMethodName = CodeNamer.Instance.GetUnique(codeModelMethodWellKnownMethodName, codeModelMethod, methodParent.IdentifiersInScope, methodParent.Children.Except(codeModelMethod.SingleItemAsEnumerable()));
                            }

                            result = nextMethodName.EqualsIgnoreCase(codeModelMethodName);
                        }
                        return result;
                    });

                if (nextMethodGroup == null || restAPIMethod.AutoRestMethod.Group == nextMethod.Group)
                {
                    nextMethodInvocation = nextMethodName;
                }
                else
                {
                    nextMethodInvocation = $"{(restAPIMethod.AutoRestMethod.Group.IsNullOrEmpty() ? "this" : "client")}.get{nextMethodGroup.ToPascalCase()}().{nextMethodName}";
                }
            }

            string nextPageLinkParameterName = null;
            string nextPageLinkVariableName = null;
            string nextGroupTypeName = null;
            string groupedTypeName = null;
            string nextMethodParameterInvocation = null;
            AutoRestParameter groupedType = null;
            if (nextMethod != null)
            {
                nextPageLinkParameterName = nextMethod.Parameters
                    .Select((AutoRestParameter parameter) => parameter.Name.Value)
                    .First((string parameterName) => parameterName.StartsWith("next", StringComparison.OrdinalIgnoreCase));

                nextPageLinkVariableName = nextPageLinkParameterName;
                if (clientMethodType != ClientMethodType.PagingSync)
                {
                    int count = 0;
                    while (clientParameters.Any((ClientParameter clientMethodParameter) => clientMethodParameter.Name == nextPageLinkVariableName))
                    {
                        ++count;
                        nextPageLinkVariableName = nextPageLinkParameterName + count;
                    }
                }

                IEnumerable<AutoRestParameter> nextMethodRestAPIParameters = nextMethod.Parameters
                    .Where((AutoRestParameter parameter) => parameter != null && !parameter.IsClientProperty && !string.IsNullOrWhiteSpace(parameter.Name))
                    .OrderBy(item => !item.IsRequired);

                AutoRestParameter nextGroupType = null;
                if (!onlyRequiredParameters)
                {
                    nextMethodParameterInvocation = string.Join(", ", nextMethodRestAPIParameters
                        .Where(p => !p.IsConstant)
                        .Select((AutoRestParameter parameter) => parameter.Name == nextPageLinkParameterName ? nextPageLinkVariableName : parameter.Name.Value));
                }
                else if (restAPIMethod.AutoRestMethod.InputParameterTransformation.IsNullOrEmpty() || nextMethod.InputParameterTransformation.IsNullOrEmpty())
                {
                    nextMethodParameterInvocation = string.Join(", ", nextMethodRestAPIParameters
                        .Select((AutoRestParameter parameter) => parameter.IsRequired ? (parameter.Name == nextPageLinkParameterName ? nextPageLinkVariableName : parameter.Name.ToString()) : "null"));
                }
                else
                {
                    groupedType = restAPIMethod.AutoRestMethod.InputParameterTransformation.First().ParameterMappings[0].InputParameter;
                    nextGroupType = nextMethod.InputParameterTransformation.First().ParameterMappings[0].InputParameter;
                    List<string> invocations = new List<string>();
                    foreach (AutoRestParameter parameter in nextMethodRestAPIParameters)
                    {
                        string parameterName = parameter.Name;

                        if (parameter.IsRequired)
                        {
                            invocations.Add(parameterName == nextPageLinkParameterName ? nextPageLinkVariableName : parameterName);
                        }
                        else if (parameterName == nextGroupType.Name && groupedType.IsRequired)
                        {
                            invocations.Add(parameterName == nextPageLinkParameterName ? nextPageLinkVariableName : parameterName);
                        }
                        else
                        {
                            invocations.Add("null");
                        }
                    }
                    nextMethodParameterInvocation = string.Join(", ", invocations);
                }

                if (restAPIMethod.IsPagingOperation && !restAPIMethod.AutoRestMethod.InputParameterTransformation.IsNullOrEmpty() && !nextMethod.InputParameterTransformation.IsNullOrEmpty())
                {
                    groupedType = groupedType ?? restAPIMethod.AutoRestMethod.InputParameterTransformation.First().ParameterMappings[0].InputParameter;
                    nextGroupType = nextGroupType ?? nextMethod.InputParameterTransformation.First().ParameterMappings[0].InputParameter;

                    if (!nextGroupType.IsClientProperty)
                    {
                        nextGroupTypeName = nextGroupType.Name;
                    }
                    else
                    {
                        string caller = (nextGroupType.Method != null && nextGroupType.Method.Group.IsNullOrEmpty() ? "this" : "this.client");
                        string clientPropertyName = nextGroupType.ClientProperty?.Name?.ToString();
                        if (!string.IsNullOrEmpty(clientPropertyName))
                        {
                            CodeNamer codeNamer = CodeNamer.Instance;
                            clientPropertyName = codeNamer.CamelCase(codeNamer.RemoveInvalidCharacters(clientPropertyName));
                        }
                        nextGroupTypeName = $"{caller}.{clientPropertyName}()";
                    }

                    if (!groupedType.IsClientProperty)
                    {
                        groupedTypeName = groupedType.Name;
                    }
                    else
                    {
                        string caller = (groupedType.Method != null && groupedType.Method.Group.IsNullOrEmpty() ? "this" : "this.client");
                        string clientPropertyName = groupedType.ClientProperty?.Name?.ToString();
                        if (!string.IsNullOrEmpty(clientPropertyName))
                        {
                            CodeNamer codeNamer = CodeNamer.Instance;
                            clientPropertyName = codeNamer.CamelCase(codeNamer.RemoveInvalidCharacters(clientPropertyName));
                        }
                        groupedTypeName = $"{caller}.{clientPropertyName}()";
                    }
                }
            }
            return new PagingNextMethodInfo(nextMethod, pageType, nextMethodInvocation, nextPageLinkParameterName, nextPageLinkVariableName, nextGroupTypeName, groupedTypeName, groupedType, nextMethodParameterInvocation);
        }

        private static IEnumerable<string> GetExpressionsToValidate(MethodJv restAPIMethod, bool onlyRequiredParameters, JavaSettings settings)
        {
            AutoRestMethod autoRestMethod = restAPIMethod.AutoRestMethod;

            List<string> expressionsToValidate = new List<string>();
            foreach (AutoRestParameter autoRestParameter in autoRestMethod.Parameters)
            {
                if (!autoRestParameter.IsConstant)
                {
                    IModelTypeJv parameterType = ModelParser.ParseType(autoRestParameter, settings);

                    if (!(parameterType is PrimitiveType) &&
                        !(parameterType is EnumType) &&
                        parameterType != ClassType.Object &&
                        parameterType != ClassType.Integer &&
                        parameterType != ClassType.Long &&
                        parameterType != ClassType.Double &&
                        parameterType != ClassType.BigDecimal &&
                        parameterType != ClassType.String &&
                        parameterType != ClassType.DateTime &&
                        parameterType != ClassType.LocalDate &&
                        parameterType != ClassType.DateTimeRfc1123 &&
                        parameterType != ClassType.Duration &&
                        parameterType != ClassType.Boolean &&
                        parameterType != ClassType.ServiceClientCredentials &&
                        parameterType != ClassType.AzureTokenCredentials &&
                        parameterType != ClassType.UUID &&
                        parameterType != ClassType.Base64Url &&
                        parameterType != ClassType.UnixTime &&
                        parameterType != ClassType.UnixTimeDateTime &&
                        parameterType != ClassType.UnixTimeLong &&
                        parameterType != ArrayType.ByteArray &&
                        parameterType != GenericType.FlowableByteBuffer &&
                        (!onlyRequiredParameters || autoRestParameter.IsRequired))
                    {
                        string parameterExpressionToValidate;
                        if (!autoRestParameter.IsClientProperty)
                        {
                            parameterExpressionToValidate = autoRestParameter.Name;
                        }
                        else
                        {
                            string caller = (autoRestParameter.Method != null && autoRestParameter.Method.Group.IsNullOrEmpty() ? "this" : "this.client");
                            string clientPropertyName = autoRestParameter.ClientProperty?.Name?.ToString();
                            if (!string.IsNullOrEmpty(clientPropertyName))
                            {
                                CodeNamer codeNamer = CodeNamer.Instance;
                                clientPropertyName = codeNamer.CamelCase(codeNamer.RemoveInvalidCharacters(clientPropertyName));
                            }
                            parameterExpressionToValidate = $"{caller}.{clientPropertyName}()";
                        }

                        expressionsToValidate.Add(parameterExpressionToValidate);
                    }
                }
            }
            return expressionsToValidate;
        }

        private static IEnumerable<ClientParameter> ParseClientMethodParameters(IEnumerable<AutoRestParameter> autoRestParameters, bool parametersAreFinal, JavaSettings settings)
        {
            List<ClientParameter> parameters = new List<ClientParameter>();
            foreach (AutoRestParameter autoRestParameter in autoRestParameters)
            {
                IModelTypeJv parameterType = ConvertToClientType(ParseType(autoRestParameter, settings));

                string parameterDescription = autoRestParameter.Documentation;
                if (string.IsNullOrEmpty(parameterDescription))
                {
                    parameterDescription = $"the {parameterType} value";
                }

                bool parameterIsRequired = autoRestParameter.IsRequired;

                IEnumerable<ClassType> parameterAnnotations = settings.GetClientMethodParameterAnnotations(parameterIsRequired);

                parameters.Add(new ClientParameter(
                    description: parameterDescription,
                    isFinal: parametersAreFinal,
                    type: parameterType,
                    name: autoRestParameter.Name,
                    isRequired: parameterIsRequired,
                    annotations: parameterAnnotations));
            }
            return parameters;
        }

        private static RequestParameterLocation ParseParameterRequestLocation(AutoRestParameterLocation autoRestParameterLocation)
        {
            RequestParameterLocation parameterRequestLocation;
            switch (autoRestParameterLocation)
            {
                case AutoRestParameterLocation.Body:
                    parameterRequestLocation = RequestParameterLocation.Body;
                    break;

                case AutoRestParameterLocation.FormData:
                    parameterRequestLocation = RequestParameterLocation.FormData;
                    break;

                case AutoRestParameterLocation.Header:
                    parameterRequestLocation = RequestParameterLocation.Header;
                    break;

                case AutoRestParameterLocation.None:
                    parameterRequestLocation = RequestParameterLocation.None;
                    break;

                case AutoRestParameterLocation.Path:
                    parameterRequestLocation = RequestParameterLocation.Path;
                    break;

                case AutoRestParameterLocation.Query:
                    parameterRequestLocation = RequestParameterLocation.Query;
                    break;

                default:
                    throw new ArgumentException("Unrecognized AutoRest ParameterLocation value: " + autoRestParameterLocation);
            }
            return parameterRequestLocation;
        }

        private static ResponseJv ParseResponse(AutoRestMethod method, JavaSettings settings)
        {
            string name = method.MethodGroup.Name.ToPascalCase() + method.Name.ToPascalCase() + "Response";
            string package = settings.Package + "." + settings.ModelsSubpackage;
            string description = $"Contains all response data for the {method.Name} operation.";
            IModelTypeJv headersType = ParseType(method.ReturnType.Headers, method.Extensions, settings).AsNullable();
            IModelTypeJv bodyType = ParseType(method.ReturnType.Body, method.Extensions, settings).AsNullable();
            return new ResponseJv(name, package, description, headersType, bodyType);
        }

        private static ServiceClient ParseServiceClient(AutoRestCodeModel codeModel, JavaSettings settings)
        {
            string serviceClientInterfaceName = AddClientTypePrefix(codeModel.Name.ToPascalCase(), settings);

            string serviceClientClassName = serviceClientInterfaceName;
            if (settings.GenerateClientInterfaces)
            {
                serviceClientClassName += "Impl";
            }

            RestAPI serviceClientRestAPI = null;
            IEnumerable<ClientMethod> serviceClientMethods = Enumerable.Empty<ClientMethod>();
            IEnumerable<AutoRestMethod> codeModelRestAPIMethods = codeModel.Methods.Where(m => m.Group.IsNullOrEmpty());
            if (codeModelRestAPIMethods.Any())
            {
                string restAPIName = serviceClientInterfaceName + "Service";
                string restAPIBaseURL = codeModel.BaseUrl;
                List<MethodJv> restAPIMethods = new List<MethodJv>();
                foreach (AutoRestMethod codeModelRestAPIMethod in codeModelRestAPIMethods)
                {
                    MethodJv restAPIMethod = ParseRestAPIMethod(codeModelRestAPIMethod, settings);
                    restAPIMethods.Add(restAPIMethod);
                }
                serviceClientRestAPI = new RestAPI(restAPIName, restAPIBaseURL, restAPIMethods);
                serviceClientMethods = ParseClientMethods(serviceClientRestAPI, settings);
            }

            List<ClientMethodGroup> serviceClientMethodGroupClients = new List<ClientMethodGroup>();
            IEnumerable<AutoRestMethodGroup> codeModelMethodGroups = codeModel.Operations.Where((AutoRestMethodGroup methodGroup) => !string.IsNullOrEmpty(methodGroup?.Name?.ToString()));
            foreach (AutoRestMethodGroup codeModelMethodGroup in codeModelMethodGroups)
            {
                serviceClientMethodGroupClients.Add(ParseMethodGroupClient(codeModelMethodGroup, serviceClientClassName, settings));
            }

            bool usesCredentials = false;

            List<ServiceClientProperty> serviceClientProperties = new List<ServiceClientProperty>();
            foreach (AutoRestProperty codeModelServiceClientProperty in codeModel.Properties)
            {
                string serviceClientPropertyDescription = codeModelServiceClientProperty.Documentation.ToString();

                string serviceClientPropertyName = CodeNamer.Instance.RemoveInvalidCharacters(codeModelServiceClientProperty.Name.ToCamelCase());

                IModelTypeJv serviceClientPropertyClientType = ConvertToClientType(ParseType(codeModelServiceClientProperty.ModelType, settings));

                bool serviceClientPropertyIsReadOnly = codeModelServiceClientProperty.IsReadOnly;

                string serviceClientPropertyDefaultValueExpression = serviceClientPropertyClientType.DefaultValueExpression(codeModelServiceClientProperty.DefaultValue);

                if (serviceClientPropertyClientType == ClassType.ServiceClientCredentials)
                {
                    usesCredentials = true;
                }
                else
                {
                    serviceClientProperties.Add(new ServiceClientProperty(serviceClientPropertyDescription, serviceClientPropertyClientType, serviceClientPropertyName, serviceClientPropertyIsReadOnly, serviceClientPropertyDefaultValueExpression));
                }
            }

            var serviceClientCredentialsParameter = new Lazy<ClientParameter>(() =>
                new ClientParameter(
                    description: "the management credentials for Azure",
                    isFinal: false,
                    type: ClassType.ServiceClientCredentials,
                    name: "credentials",
                    isRequired: true,
                    annotations: settings.GetClientMethodParameterAnnotations(true)));

            var azureEnvironmentParameter = new Lazy<ClientParameter>(() =>
                new ClientParameter(
                    description: "The environment that requests will target.",
                    isFinal: false,
                    type: ClassType.AzureEnvironment,
                    name: "azureEnvironment",
                    isRequired: true,
                    annotations: settings.GetClientMethodParameterAnnotations(true)));

            var httpPipelineParameter = new Lazy<ClientParameter>(() =>
                new ClientParameter(
                    description: "The HTTP pipeline to send requests through.",
                    isFinal: false,
                    type: ClassType.HttpPipeline,
                    name: "httpPipeline",
                    isRequired: true,
                    annotations: settings.GetClientMethodParameterAnnotations(true)));

            List<Constructor> serviceClientConstructors = new List<Constructor>();
            string constructorDescription = $"Initializes an instance of {serviceClientInterfaceName} client.";
            if (settings.IsAzureOrFluent)
            {
                if (usesCredentials)
                {
                    serviceClientConstructors.Add(new Constructor(serviceClientCredentialsParameter.Value));
                    serviceClientConstructors.Add(new Constructor(serviceClientCredentialsParameter.Value, azureEnvironmentParameter.Value));
                }
                else
                {
                    serviceClientConstructors.Add(new Constructor());
                    serviceClientConstructors.Add(new Constructor(azureEnvironmentParameter.Value));
                }

                serviceClientConstructors.Add(new Constructor(httpPipelineParameter.Value));
                serviceClientConstructors.Add(new Constructor(httpPipelineParameter.Value, azureEnvironmentParameter.Value));
            }
            else
            {
                serviceClientConstructors.Add(new Constructor());
                serviceClientConstructors.Add(new Constructor(httpPipelineParameter.Value));
            }

            return new ServiceClient(serviceClientClassName, serviceClientInterfaceName, serviceClientRestAPI, serviceClientMethodGroupClients, serviceClientProperties, serviceClientConstructors, serviceClientMethods, serviceClientCredentialsParameter, azureEnvironmentParameter, httpPipelineParameter);
        }

        private static IEnumerable<ClientException> ParseExceptions(AutoRestCodeModel codeModel, JavaSettings settings)
        {
            List<ClientException> exceptions = new List<ClientException>();
            foreach (AutoRestCompositeType exceptionType in codeModel.ErrorTypes)
            {
                string errorName = exceptionType.Name.ToString();
                if (settings.IsFluent && !string.IsNullOrEmpty(errorName) && innerModelCompositeType.Contains(exceptionType))
                {
                    errorName += "Inner";
                }

                string methodOperationExceptionTypeName = errorName + "Exception";

                if (exceptionType.Extensions.ContainsKey(SwaggerExtensions.NameOverrideExtension))
                {
                    JContainer ext = exceptionType.Extensions[SwaggerExtensions.NameOverrideExtension] as JContainer;
                    if (ext != null && ext["name"] != null)
                    {
                        methodOperationExceptionTypeName = ext["name"].ToString();
                    }
                }

                // Skip any exceptions that are named "CloudErrorException" or have a body named
                // "CloudError" because those types already exist in the runtime.
                if (methodOperationExceptionTypeName != "CloudErrorException" && errorName != "CloudError")
                {
                    string exceptionSubPackage;
                    if (settings.IsFluent)
                    {
                        exceptionSubPackage = innerModelCompositeType.Contains(exceptionType) ? settings.ImplementationSubpackage : "";
                    }
                    else
                    {
                        exceptionSubPackage = settings.ModelsSubpackage;
                    }

                    exceptions.Add(new ClientException(methodOperationExceptionTypeName, errorName, exceptionSubPackage));
                }
            }
            return exceptions;
        }

        private static IEnumerable<XmlSequenceWrapper> ParseXmlSequenceWrappers(AutoRestCodeModel codeModel, JavaSettings settings)
        {
            List<XmlSequenceWrapper> xmlSequenceWrappers = new List<XmlSequenceWrapper>();
            if (codeModel.ShouldGenerateXmlSerialization)
            {
                // Every sequence type used as a parameter to a service method.
                IEnumerable<AutoRestMethod> allMethods = codeModel.Methods.Concat(codeModel.Operations.SelectMany(methodGroup => methodGroup.Methods));
                IEnumerable<AutoRestParameter> allParameters = allMethods.SelectMany(method => method.Parameters);

                foreach (AutoRestParameter parameter in allParameters)
                {
                    IModelTypeJv parameterType = ParseType(parameter.ModelType, settings);

                    if (parameterType is ListType parameterListType && parameter.ModelType is AutoRestSequenceType sequenceType)
                    {
                        string xmlRootElementName = sequenceType.XmlName;
                        string xmlListElementName = sequenceType.ElementType.XmlProperties?.Name ?? sequenceType.ElementXmlName;
                        if (!xmlSequenceWrappers.Any(existingWrapper => existingWrapper.XmlRootElementName == xmlRootElementName && existingWrapper.XmlListElementName == xmlListElementName))
                        {
                            HashSet<string> xmlSequenceWrapperImports = new HashSet<string>()
                            {
                                "com.fasterxml.jackson.annotation.JsonCreator",
                                "com.fasterxml.jackson.annotation.JsonProperty",
                                "com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty",
                                "com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement"
                            };
                            parameterListType.AddImportsTo(xmlSequenceWrapperImports, true);

                            xmlSequenceWrappers.Add(new XmlSequenceWrapper(parameterListType, xmlRootElementName, xmlListElementName, xmlSequenceWrapperImports));
                        }
                    }
                }
            }
            return xmlSequenceWrappers;
        }

        private static IEnumerable<ClientMethod> ParseClientMethods(RestAPI restAPI, JavaSettings settings)
        {
            List<ClientMethod> clientMethods = new List<ClientMethod>();

            foreach (MethodJv restAPIMethod in restAPI.Methods)
            {
                IEnumerable<AutoRestParameter> autoRestClientMethodAndConstantParameters = restAPIMethod.AutoRestMethod.Parameters
                    //Omit parameter-group properties for now since Java doesn't support them yet
                    .Where((AutoRestParameter autoRestParameter) => autoRestParameter != null && !autoRestParameter.IsClientProperty && !string.IsNullOrWhiteSpace(autoRestParameter.Name))
                    .OrderBy(item => !item.IsRequired);
                IEnumerable<AutoRestParameter> autoRestClientMethodParameters = autoRestClientMethodAndConstantParameters
                    .Where((AutoRestParameter autoRestParameter) => !autoRestParameter.IsConstant)
                    .OrderBy((AutoRestParameter autoRestParameter) => !autoRestParameter.IsRequired);
                IEnumerable<AutoRestParameter> autoRestRequiredClientMethodParameters = autoRestClientMethodParameters
                    .Where(parameter => parameter.IsRequired);

                AutoRestResponse autoRestRestAPIMethodReturnType = restAPIMethod.AutoRestMethod.ReturnType;
                AutoRestIModelType autoRestRestAPIMethodReturnBodyType = autoRestRestAPIMethodReturnType.Body ?? DependencyInjection.New<AutoRestPrimaryType>(AutoRestKnownPrimaryType.None);

                IModelTypeJv restAPIMethodReturnBodyWireType = ParseType(autoRestRestAPIMethodReturnBodyType, settings);
                IModelTypeJv restAPIMethodReturnBodyClientType = ConvertToClientType(restAPIMethodReturnBodyWireType);

                GenericType pageImplType = null;
                IModelTypeJv deserializedResponseBodyType;
                IModelTypeJv pageType;

                if (settings.IsAzureOrFluent &&
                    restAPIMethodReturnBodyClientType is ListType restAPIMethodReturnBodyClientListType &&
                    (restAPIMethod.IsPagingOperation || restAPIMethod.IsPagingNextOperation || restAPIMethod.SimulateAsPagingOperation))
                {
                    IModelTypeJv restAPIMethodReturnBodyClientListElementType = restAPIMethodReturnBodyClientListType.ElementType;

                    restAPIMethodReturnBodyClientType = GenericType.PagedList(restAPIMethodReturnBodyClientListElementType);

                    string pageImplTypeName = SequenceTypeGetPageImplType(autoRestRestAPIMethodReturnBodyType);

                    string pageImplSubPackage = settings.IsFluent ? settings.ImplementationSubpackage : settings.ModelsSubpackage;
                    string pageImplPackage = $"{settings.Package}.{pageImplSubPackage}";

                    pageImplType = new GenericType(pageImplPackage, pageImplTypeName, restAPIMethodReturnBodyClientListElementType);
                    deserializedResponseBodyType = pageImplType;

                    pageType = GenericType.Page(restAPIMethodReturnBodyClientListElementType);
                }
                else
                {
                    deserializedResponseBodyType = restAPIMethodReturnBodyClientType;

                    pageType = restAPIMethodReturnBodyClientType.AsNullable();
                }

                ClientParameter serviceCallbackParameter = new ClientParameter(
                    description: "the async ServiceCallback to handle successful and failed responses.",
                    isFinal: false,
                    type: GenericType.ServiceCallback(restAPIMethodReturnBodyClientType),
                    name: "serviceCallback",
                    isRequired: true,
                    annotations: settings.GetClientMethodParameterAnnotations(false));

                GenericType serviceFutureClientReturnType = GenericType.ServiceFuture(restAPIMethodReturnBodyClientType);
                GenericType serviceFutureWireReturnType = GenericType.ServiceFuture(restAPIMethodReturnBodyWireType);

                GenericType observablePageType = GenericType.Observable(pageType);
                GenericType observablePageImplType = GenericType.Observable(pageImplType);

                List<IEnumerable<AutoRestParameter>> autoRestParameterLists = new List<IEnumerable<AutoRestParameter>>()
                {
                    autoRestClientMethodParameters
                };
                if (settings.RequiredParameterClientMethods && autoRestClientMethodParameters.Any(parameter => !parameter.IsRequired))
                {
                    autoRestParameterLists.Insert(0, autoRestRequiredClientMethodParameters);
                }

                bool addSimpleClientMethods = true;

                if (settings.IsAzureOrFluent)
                {
                    if (restAPIMethod.IsResumable)
                    {
                        var opDefParam = restAPIMethod.Parameters.First();
                        var parameters = new List<ClientParameter>();
                        var expressionsToValidate = new List<string>();
                        parameters.Add(
                            new ClientParameter(
                                opDefParam.Description,
                                false,
                                opDefParam.Type,
                                opDefParam.Name, true,
                                new List<ClassType>()));
                        clientMethods.Add(new ClientMethod(
                            description: restAPIMethod.Description + " (resume watch)",
                            returnValue: new ReturnValue(
                                description: "the observable for the request",
                                wireType: GenericType.Observable(GenericType.OperationStatus(restAPIMethodReturnBodyWireType)),
                                clientType: GenericType.Observable(GenericType.OperationStatus(restAPIMethodReturnBodyClientType))),
                            name: restAPIMethod.Name,
                            parameters: parameters,
                            onlyRequiredParameters: true,
                            type: ClientMethodType.Resumable,
                            restAPIMethod: restAPIMethod,
                            expressionsToValidate: expressionsToValidate,
                            pagingNextMethodInfo: GetPagingNextMethodInfo(restAPIMethod, ClientMethodType.Resumable, parameters, true, settings),
                            serviceCallbackParameter: serviceCallbackParameter));

                        addSimpleClientMethods = false;
                    }
                    else if (restAPIMethod.IsPagingOperation || restAPIMethod.IsPagingNextOperation)
                    {
                        foreach (IEnumerable<AutoRestParameter> autoRestParameters in autoRestParameterLists)
                        {
                            bool onlyRequiredParameters = (autoRestParameters == autoRestRequiredClientMethodParameters);

                            IEnumerable<string> expressionsToValidate = GetExpressionsToValidate(restAPIMethod, onlyRequiredParameters, settings);

                            IEnumerable<ClientParameter> parameters = ParseClientMethodParameters(autoRestParameters, false, settings);

                            clientMethods.Add(new ClientMethod(
                                description: restAPIMethod.Description,
                                returnValue: new ReturnValue(
                                    description: restAPIMethodReturnBodyClientType == PrimitiveType.Void ? null : $"the {restAPIMethodReturnBodyClientType} object if successful.",
                                    wireType: restAPIMethodReturnBodyWireType,
                                    clientType: restAPIMethodReturnBodyClientType),
                                name: restAPIMethod.Name,
                                parameters: parameters,
                                onlyRequiredParameters: onlyRequiredParameters,
                                type: ClientMethodType.PagingSync,
                                restAPIMethod: restAPIMethod,
                                expressionsToValidate: expressionsToValidate,
                                pagingNextMethodInfo: GetPagingNextMethodInfo(restAPIMethod, ClientMethodType.PagingSync, parameters, onlyRequiredParameters, settings),
                                serviceCallbackParameter: serviceCallbackParameter));

                            clientMethods.Add(new ClientMethod(
                                description: restAPIMethod.Description,
                                returnValue: new ReturnValue(
                                    description: restAPIMethodReturnBodyClientType == PrimitiveType.Void ? $"the {observablePageType} object if successful." : $"the observable to the {restAPIMethodReturnBodyClientType} object",
                                    wireType: observablePageImplType,
                                    clientType: observablePageType),
                                name: restAPIMethod.Name + "Async",
                                parameters: parameters,
                                onlyRequiredParameters: onlyRequiredParameters,
                                type: ClientMethodType.PagingAsync,
                                restAPIMethod: restAPIMethod,
                                expressionsToValidate: expressionsToValidate,
                                pagingNextMethodInfo: GetPagingNextMethodInfo(restAPIMethod, ClientMethodType.PagingAsync, parameters, onlyRequiredParameters, settings),
                                serviceCallbackParameter: serviceCallbackParameter));

                            GenericType singlePageMethodReturnType = GenericType.Single(pageType);
                            GenericType singlePageImplMethodReturnType = GenericType.Single(pageImplType);
                            clientMethods.Add(new ClientMethod(
                                description: restAPIMethod.Description,
                                returnValue: new ReturnValue(
                                    description: $"the {singlePageMethodReturnType} object if successful.",
                                    wireType: singlePageImplMethodReturnType,
                                    clientType: singlePageMethodReturnType),
                                name: restAPIMethod.Name.Async(),
                                parameters: parameters,
                                onlyRequiredParameters: onlyRequiredParameters,
                                type: ClientMethodType.PagingAsyncSinglePage,
                                restAPIMethod: restAPIMethod,
                                expressionsToValidate: expressionsToValidate,
                                pagingNextMethodInfo: GetPagingNextMethodInfo(restAPIMethod, ClientMethodType.PagingAsyncSinglePage, parameters, onlyRequiredParameters, settings),
                                serviceCallbackParameter: serviceCallbackParameter));
                        }

                        addSimpleClientMethods = false;
                    }
                    else if (restAPIMethod.SimulateAsPagingOperation)
                    {
                        foreach (IEnumerable<AutoRestParameter> autoRestParameters in autoRestParameterLists)
                        {
                            bool onlyRequiredParameters = (autoRestParameters == autoRestRequiredClientMethodParameters);

                            IEnumerable<string> expressionsToValidate = GetExpressionsToValidate(restAPIMethod, onlyRequiredParameters, settings);

                            IEnumerable<ClientParameter> parameters = ParseClientMethodParameters(autoRestParameters, false, settings);

                            clientMethods.Add(new ClientMethod(
                                description: restAPIMethod.Description,
                                returnValue: new ReturnValue(
                                    description: restAPIMethodReturnBodyClientType == PrimitiveType.Void ? null : $"the {restAPIMethodReturnBodyClientType} object if successful.",
                                    wireType: restAPIMethodReturnBodyWireType,
                                    clientType: restAPIMethodReturnBodyClientType),
                                name: restAPIMethod.Name,
                                parameters: parameters,
                                onlyRequiredParameters: onlyRequiredParameters,
                                type: ClientMethodType.SimulatedPagingSync,
                                restAPIMethod: restAPIMethod,
                                expressionsToValidate: expressionsToValidate,
                                pagingNextMethodInfo: GetPagingNextMethodInfo(restAPIMethod, ClientMethodType.SimulatedPagingSync, parameters, onlyRequiredParameters, settings),
                                serviceCallbackParameter: serviceCallbackParameter));

                            clientMethods.Add(new ClientMethod(
                                description: restAPIMethod.Description,
                                returnValue: new ReturnValue(
                                    description: restAPIMethodReturnBodyClientType == PrimitiveType.Void ? $"the {observablePageType} object if successful." : $"the observable to the {restAPIMethodReturnBodyClientType} object",
                                    wireType: GenericType.Observable(pageImplType),
                                    clientType: GenericType.Observable(pageType)),
                                name: restAPIMethod.Name.Async(),
                                parameters: parameters,
                                onlyRequiredParameters: onlyRequiredParameters,
                                type: ClientMethodType.SimulatedPagingAsync,
                                restAPIMethod: restAPIMethod,
                                expressionsToValidate: expressionsToValidate,
                                pagingNextMethodInfo: GetPagingNextMethodInfo(restAPIMethod, ClientMethodType.SimulatedPagingAsync, parameters, onlyRequiredParameters, settings),
                                serviceCallbackParameter: serviceCallbackParameter));
                        }

                        addSimpleClientMethods = false;
                    }
                    else if (restAPIMethod.IsLongRunningOperation)
                    {
                        foreach (IEnumerable<AutoRestParameter> autoRestParameters in autoRestParameterLists)
                        {
                            bool onlyRequiredParameters = (autoRestParameters == autoRestRequiredClientMethodParameters);

                            IEnumerable<string> expressionsToValidate = GetExpressionsToValidate(restAPIMethod, onlyRequiredParameters, settings);

                            IEnumerable<ClientParameter> parameters = ParseClientMethodParameters(autoRestParameters, false, settings);

                            clientMethods.Add(new ClientMethod(
                                description: restAPIMethod.Description,
                                returnValue: new ReturnValue(
                                    description: restAPIMethodReturnBodyClientType == PrimitiveType.Void ? null : $"the {restAPIMethodReturnBodyClientType} object if successful.",
                                    wireType: restAPIMethodReturnBodyWireType,
                                    clientType: restAPIMethodReturnBodyClientType),
                                name: restAPIMethod.Name,
                                parameters: parameters,
                                onlyRequiredParameters: onlyRequiredParameters,
                                type: ClientMethodType.LongRunningSync,
                                restAPIMethod: restAPIMethod,
                                expressionsToValidate: expressionsToValidate,
                                pagingNextMethodInfo: GetPagingNextMethodInfo(restAPIMethod, ClientMethodType.LongRunningSync, parameters, onlyRequiredParameters, settings),
                                serviceCallbackParameter: serviceCallbackParameter));

                            clientMethods.Add(new ClientMethod(
                                description: restAPIMethod.Description,
                                returnValue: new ReturnValue(
                                    description: $"the {serviceFutureClientReturnType} object",
                                    wireType: serviceFutureWireReturnType,
                                    clientType: serviceFutureClientReturnType),
                                name: restAPIMethod.Name.Async(),
                                parameters: parameters.ConcatSingleItem(serviceCallbackParameter),
                                onlyRequiredParameters: onlyRequiredParameters,
                                type: ClientMethodType.LongRunningAsyncServiceCallback,
                                restAPIMethod: restAPIMethod,
                                expressionsToValidate: expressionsToValidate,
                                pagingNextMethodInfo: GetPagingNextMethodInfo(restAPIMethod, ClientMethodType.LongRunningAsyncServiceCallback, parameters, onlyRequiredParameters, settings),
                                serviceCallbackParameter: serviceCallbackParameter));

                            clientMethods.Add(new ClientMethod(
                                description: restAPIMethod.Description,
                                returnValue: new ReturnValue(
                                    description: "the observable for the request",
                                    wireType: GenericType.Observable(GenericType.OperationStatus(restAPIMethodReturnBodyWireType)),
                                    clientType: GenericType.Observable(GenericType.OperationStatus(restAPIMethodReturnBodyClientType))),
                                name: restAPIMethod.Name.Async(),
                                parameters: parameters,
                                onlyRequiredParameters: onlyRequiredParameters,
                                type: ClientMethodType.LongRunningAsync,
                                restAPIMethod: restAPIMethod,
                                expressionsToValidate: expressionsToValidate,
                                pagingNextMethodInfo: GetPagingNextMethodInfo(restAPIMethod, ClientMethodType.LongRunningAsync, parameters, onlyRequiredParameters, settings),
                                serviceCallbackParameter: serviceCallbackParameter));
                        }

                        addSimpleClientMethods = false;
                    }
                }

                if (addSimpleClientMethods)
                {
                    bool isFluentDelete = settings.IsFluent && restAPIMethod.Name.EqualsIgnoreCase(Delete) && autoRestRequiredClientMethodParameters.Count() == 2;

                    foreach (IEnumerable<AutoRestParameter> autoRestParameters in autoRestParameterLists)
                    {
                        bool onlyRequiredParameters = (autoRestParameters == autoRestRequiredClientMethodParameters);

                        IEnumerable<string> expressionsToValidate = GetExpressionsToValidate(restAPIMethod, onlyRequiredParameters, settings);

                        IEnumerable<ClientParameter> parameters = ParseClientMethodParameters(autoRestParameters, false, settings);

                        clientMethods.Add(new ClientMethod(
                            description: restAPIMethod.Description,
                            returnValue: new ReturnValue(
                                description: restAPIMethodReturnBodyClientType == PrimitiveType.Void ? null : $"the {restAPIMethodReturnBodyClientType} object if successful.",
                                wireType: restAPIMethodReturnBodyWireType,
                                clientType: restAPIMethodReturnBodyClientType),
                            name: restAPIMethod.Name,
                            parameters: parameters,
                            onlyRequiredParameters: onlyRequiredParameters,
                            type: ClientMethodType.SimpleSync,
                            restAPIMethod: restAPIMethod,
                            expressionsToValidate: expressionsToValidate,
                            pagingNextMethodInfo: GetPagingNextMethodInfo(restAPIMethod, ClientMethodType.SimpleSync, parameters, onlyRequiredParameters, settings),
                            serviceCallbackParameter: serviceCallbackParameter));

                        clientMethods.Add(new ClientMethod(
                            description: restAPIMethod.Description,
                            returnValue: new ReturnValue(
                                description: $"a ServiceFuture which will be completed with the result of the network request.",
                                wireType: serviceFutureWireReturnType,
                                clientType: serviceFutureClientReturnType),
                            name: restAPIMethod.Name.Async(),
                            parameters: parameters.ConcatSingleItem(serviceCallbackParameter),
                            onlyRequiredParameters: onlyRequiredParameters,
                            type: ClientMethodType.SimpleAsyncServiceCallback,
                            restAPIMethod: restAPIMethod,
                            expressionsToValidate: expressionsToValidate,
                            pagingNextMethodInfo: GetPagingNextMethodInfo(restAPIMethod, ClientMethodType.SimpleAsyncServiceCallback, parameters, onlyRequiredParameters, settings),
                            serviceCallbackParameter: serviceCallbackParameter));

                        clientMethods.Add(new ClientMethod(
                            description: restAPIMethod.Description,
                            returnValue: new ReturnValue(
                                description: $"a Single which performs the network request upon subscription.",
                                wireType: restAPIMethod.AsyncReturnType,
                                clientType: ConvertToClientType(restAPIMethod.AsyncReturnType)),
                            name: GetSimpleAsyncRestResponseMethodName(restAPIMethod),
                            parameters: parameters,
                            onlyRequiredParameters: onlyRequiredParameters,
                            type: ClientMethodType.SimpleAsyncRestResponse,
                            restAPIMethod: restAPIMethod,
                            expressionsToValidate: expressionsToValidate,
                            pagingNextMethodInfo: GetPagingNextMethodInfo(restAPIMethod, ClientMethodType.SimpleAsyncRestResponse, parameters, onlyRequiredParameters, settings),
                            serviceCallbackParameter: serviceCallbackParameter));

                        IModelTypeJv asyncMethodWireReturnType;
                        IModelTypeJv asyncMethodClientReturnType;
                        if (restAPIMethodReturnBodyClientType != PrimitiveType.Void)
                        {
                            asyncMethodWireReturnType = GenericType.Maybe(restAPIMethodReturnBodyWireType);
                            asyncMethodClientReturnType = GenericType.Maybe(restAPIMethodReturnBodyClientType);
                        }
                        else if (isFluentDelete)
                        {
                            asyncMethodWireReturnType = GenericType.Maybe(ClassType.Void);
                            asyncMethodClientReturnType = GenericType.Maybe(ClassType.Void);
                        }
                        else
                        {
                            asyncMethodWireReturnType = ClassType.Completable;
                            asyncMethodClientReturnType = ClassType.Completable;
                        }
                        clientMethods.Add(new ClientMethod(
                            description: restAPIMethod.Description,
                            returnValue: new ReturnValue(
                                description: $"a Single which performs the network request upon subscription.",
                                wireType: asyncMethodWireReturnType,
                                clientType: asyncMethodClientReturnType),
                            name: restAPIMethod.Name.Async(),
                            parameters: parameters,
                            onlyRequiredParameters: onlyRequiredParameters,
                            type: ClientMethodType.SimpleAsync,
                            restAPIMethod: restAPIMethod,
                            expressionsToValidate: expressionsToValidate,
                            pagingNextMethodInfo: GetPagingNextMethodInfo(restAPIMethod, ClientMethodType.SimpleAsync, parameters, onlyRequiredParameters, settings),
                            serviceCallbackParameter: serviceCallbackParameter));
                    }
                }
            }

            return clientMethods;
        }

        private static ClientModel ParseModel(AutoRestCompositeType autoRestCompositeType, JavaSettings settings, ClientModels serviceModels)
        {
            string modelName = autoRestCompositeType.Name.ToString();
            if (settings.IsFluent && !string.IsNullOrEmpty(modelName) && innerModelCompositeType.Contains(autoRestCompositeType))
            {
                modelName += "Inner";
            }

            ClientModel result = serviceModels.GetModel(modelName);
            if (result == null)
            {
                string modelSubPackage = !settings.IsFluent ? settings.ModelsSubpackage : (innerModelCompositeType.Contains(autoRestCompositeType) ? settings.ImplementationSubpackage : "");
                string modelPackage = settings.GetSubPackage(modelSubPackage);

                bool isPolymorphic = autoRestCompositeType.BaseIsPolymorphic;

                ClientModel parentModel = null;
                if (autoRestCompositeType.BaseModelType != null)
                {
                    parentModel = ParseModel(autoRestCompositeType.BaseModelType, settings, serviceModels);
                }

                HashSet<string> modelImports = new HashSet<string>();
                IEnumerable<AutoRestProperty> compositeTypeProperties = autoRestCompositeType.Properties;
                foreach (AutoRestProperty autoRestProperty in compositeTypeProperties)
                {
                    IModelTypeJv propertyType = ParseType(autoRestProperty.ModelType, settings);
                    propertyType.AddImportsTo(modelImports, false);

                    IModelTypeJv propertyClientType = ConvertToClientType(propertyType);
                    propertyClientType.AddImportsTo(modelImports, false);
                }

                if (compositeTypeProperties.Any())
                {
                    if (settings.ShouldGenerateXmlSerialization)
                    {
                        modelImports.Add("com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement");

                        if (compositeTypeProperties.Any(p => p.ModelType is AutoRestSequenceType))
                        {
                            modelImports.Add("java.util.ArrayList");
                        }

                        if (compositeTypeProperties.Any(p => p.XmlIsAttribute))
                        {
                            modelImports.Add("com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty");
                        }

                        if (compositeTypeProperties.Any(p => !p.XmlIsAttribute))
                        {
                            modelImports.Add("com.fasterxml.jackson.annotation.JsonProperty");
                        }

                        if (compositeTypeProperties.Any(p => p.XmlIsWrapped))
                        {
                            modelImports.Add("com.fasterxml.jackson.annotation.JsonCreator");
                        }
                    }
                    else
                    {
                        modelImports.Add("com.fasterxml.jackson.annotation.JsonProperty");
                    }
                }

                string modelDescription;
                if (string.IsNullOrEmpty(autoRestCompositeType.Summary) && string.IsNullOrEmpty(autoRestCompositeType.Documentation))
                {
                    modelDescription = $"The {modelName} model.";
                }
                else
                {
                    modelDescription = $"{autoRestCompositeType.Summary}{autoRestCompositeType.Documentation}";
                }

                string polymorphicDiscriminator = autoRestCompositeType.BasePolymorphicDiscriminator;

                string modelSerializedName = autoRestCompositeType.SerializedName;

                IEnumerable<ClientModel> derivedTypes = serviceModels.GetDerivedTypes(modelName);

                string modelXmlName = autoRestCompositeType.XmlName;

                bool needsFlatten = false;
                List<ClientModelProperty> properties = new List<ClientModelProperty>();
                foreach (AutoRestProperty property in compositeTypeProperties)
                {
                    properties.Add(ParseModelProperty(property, settings));
                    if (!needsFlatten && property.WasFlattened())
                    {
                        needsFlatten = true;
                    }
                }

                bool skipParentValidation = skipParentValidationTypes.Contains(autoRestCompositeType);

                result = new ClientModel(modelPackage, modelName, modelImports, modelDescription, isPolymorphic, polymorphicDiscriminator, modelSerializedName, needsFlatten, parentModel, derivedTypes, modelXmlName, properties, skipParentValidation);

                serviceModels.AddModel(result);
            }

            return result;
        }

        private static ClientModelProperty ParseModelProperty(AutoRestProperty autoRestProperty, JavaSettings settings)
        {
            string name = autoRestProperty?.Name?.ToString();
            if (!string.IsNullOrEmpty(name))
            {
                CodeNamer codeNamer = CodeNamer.Instance;
                name = codeNamer.CamelCase(codeNamer.RemoveInvalidCharacters(name));
            }

            string description = "";
            if (string.IsNullOrEmpty(autoRestProperty.Summary) && string.IsNullOrEmpty(autoRestProperty.Documentation))
            {
                description = $"The {name} property.";
            }
            else
            {
                description = autoRestProperty.Summary;

                string documentation = autoRestProperty.Documentation;
                if (!string.IsNullOrEmpty(documentation))
                {
                    if (!string.IsNullOrEmpty(description))
                    {
                        description += Environment.NewLine;
                    }
                    description += documentation;
                }
            }

            string xmlName;
            try
            {
                xmlName = autoRestProperty.ModelType.XmlProperties?.Name
                    ?? autoRestProperty.XmlName;
            }
            catch
            {
                xmlName = null;
            }

            List<string> annotationArgumentList = new List<string>()
            {
                $"value = \"{(settings.ShouldGenerateXmlSerialization ? xmlName : autoRestProperty.SerializedName)}\""
            };
            if (autoRestProperty.IsRequired)
            {
                annotationArgumentList.Add("required = true");
            }
            if (autoRestProperty.IsReadOnly)
            {
                annotationArgumentList.Add("access = JsonProperty.Access.WRITE_ONLY");
            }
            string annotationArguments = string.Join(", ", annotationArgumentList);

            bool isXmlAttribute = autoRestProperty.XmlIsAttribute;

            string serializedName = autoRestProperty.SerializedName;

            bool isXmlWrapper = autoRestProperty.XmlIsWrapped;

            string headerCollectionPrefix = GetExtensionString(autoRestProperty.Extensions, SwaggerExtensions.HeaderCollectionPrefix);

            IModelTypeJv propertyWireType = ParseType(autoRestProperty, settings);

            IModelTypeJv propertyClientType = ConvertToClientType(propertyWireType);

            AutoRestIModelType autoRestPropertyModelType = autoRestProperty.ModelType;
            string xmlListElementName = null;
            if (autoRestPropertyModelType is AutoRestSequenceType sequence)
            {
                try
                {
                    xmlListElementName = sequence.ElementType.XmlProperties?.Name ?? sequence.ElementXmlName;
                }
                catch { }
            }

            bool isConstant = autoRestProperty.IsConstant;

            string defaultValue;
            try
            {
                defaultValue = propertyWireType.DefaultValueExpression(autoRestProperty.DefaultValue);
            }
            catch (NotSupportedException)
            {
                defaultValue = null;
            }

            bool isReadOnly = autoRestProperty.IsReadOnly;

            bool wasFlattened = autoRestProperty.WasFlattened();

            return new ClientModelProperty(name, description, annotationArguments, isXmlAttribute, xmlName, serializedName, isXmlWrapper, xmlListElementName, propertyWireType, propertyClientType, isConstant, defaultValue, isReadOnly, wasFlattened, headerCollectionPrefix);
        }

        private static ClientManager ParseManager(ServiceClient serviceClient, AutoRestCodeModel codeModel, JavaSettings settings)
        {
            ClientManager manager = null;
            if (settings.IsFluent && settings.RegenerateManagers)
            {
                string serviceName = GetServiceName(settings.ServiceName, codeModel);
                if (string.IsNullOrEmpty(serviceName))
                {
                    serviceName = "MissingServiceName";
                }

                var azureTokenCredentialsParameter = new Lazy<ClientParameter>(() =>
                    new ClientParameter(
                        description: "the management credentials for Azure",
                        isFinal: false,
                        type: ClassType.AzureTokenCredentials,
                        name: "credentials",
                        isRequired: true,
                        annotations: settings.GetClientMethodParameterAnnotations(true)));

                manager = new ClientManager(codeModel.Name, serviceName, azureTokenCredentialsParameter, serviceClient.HttpPipelineParameter);
            }
            return manager;
        }

        internal static IModelTypeJv ParseType(AutoRestIVariable autoRestIVariable, JavaSettings settings)
        {
            IModelTypeJv result = ParseType(autoRestIVariable?.ModelType, autoRestIVariable?.Extensions, settings);
            if (result != null && autoRestIVariable.IsNullable())
            {
                result = result.AsNullable();
            }
            return result;
        }

        internal static IModelTypeJv ParseType(AutoRestIModelType autoRestIModelType, IDictionary<string, object> extensions, JavaSettings settings)
        {
            string headerCollectionPrefix = GetExtensionString(extensions, SwaggerExtensions.HeaderCollectionPrefix);
            return ParseType(autoRestIModelType, headerCollectionPrefix, settings);
        }

        internal static IModelTypeJv ParseType(AutoRestIModelType autoRestIModelType, string headerCollectionPrefix, JavaSettings settings)
        {
            IModelTypeJv result;
            if (!string.IsNullOrEmpty(headerCollectionPrefix))
            {
                result = new MapType(ClassType.String);
            }
            else
            {
                result = ParseType(autoRestIModelType, settings);
            }
            return result;
        }

        internal static IModelTypeJv ParseType(AutoRestIModelType autoRestIModelType, JavaSettings settings)
        {
            IModelTypeJv result = null;
            if (autoRestIModelType == null)
            {
                result = PrimitiveType.Void;
            }
            else if (parsedAutoRestIModelTypes.ContainsKey(autoRestIModelType))
            {
                result = parsedAutoRestIModelTypes[autoRestIModelType];
            }
            else
            {
                if (autoRestIModelType is AutoRestSequenceType autoRestSequenceType)
                {
                    result = new ListType(ParseType(autoRestSequenceType.ElementType, settings));
                }
                else if (autoRestIModelType is AutoRestDictionaryType autoRestDictionaryType)
                {
                    result = new MapType(ParseType(autoRestDictionaryType.ValueType, settings));
                }
                else if (autoRestIModelType is AutoRestEnumType autoRestEnumType)
                {
                    result = ParseEnumType(autoRestEnumType, settings);
                }
                else if (autoRestIModelType is AutoRestCompositeType autoRestCompositeType)
                {
                    string classTypeName = AutoRestCompositeTypeName(autoRestCompositeType, settings);
                    if (settings.IsAzureOrFluent)
                    {
                        if (classTypeName == ClassType.Resource.Name)
                        {
                            result = ClassType.Resource;
                        }
                        else if (classTypeName == ClassType.SubResource.Name)
                        {
                            result = ClassType.SubResource;
                        }
                    }

                    if (result == null)
                    {
                        bool isInnerModelType = innerModelCompositeType.Contains(autoRestCompositeType);

                        string classPackage;
                        if (!settings.IsFluent)
                        {
                            classPackage = settings.GetSubPackage(settings.ModelsSubpackage);
                        }
                        else if (isInnerModelType)
                        {
                            classPackage = settings.GetSubPackage(settings.ImplementationSubpackage);
                        }
                        else
                        {
                            classPackage = settings.GetSubPackage();
                        }

                        IDictionary<string, string> extensions = null;
                        if (autoRestCompositeType.Extensions.ContainsKey(SwaggerExtensions.NameOverrideExtension))
                        {
                            JContainer ext = autoRestCompositeType.Extensions[SwaggerExtensions.NameOverrideExtension] as JContainer;
                            if (ext != null && ext["name"] != null)
                            {
                                extensions = new Dictionary<string, string>();
                                extensions[SwaggerExtensions.NameOverrideExtension] = ext["name"].ToString();
                            }
                        }
                        result = new ClassType(classPackage, classTypeName, null, extensions, isInnerModelType);
                    }
                }
                else if (autoRestIModelType is AutoRestPrimaryType autoRestPrimaryType)
                {
                    switch (autoRestPrimaryType.KnownPrimaryType)
                    {
                        case AutoRestKnownPrimaryType.None:
                            result = PrimitiveType.Void;
                            break;
                        case AutoRestKnownPrimaryType.Base64Url:
                            result = ClassType.Base64Url;
                            break;
                        case AutoRestKnownPrimaryType.Boolean:
                            result = PrimitiveType.Boolean;
                            break;
                        case AutoRestKnownPrimaryType.ByteArray:
                            result = ArrayType.ByteArray;
                            break;
                        case AutoRestKnownPrimaryType.Date:
                            result = ClassType.LocalDate;
                            break;
                        case AutoRestKnownPrimaryType.DateTime:
                            result = ClassType.DateTime;
                            break;
                        case AutoRestKnownPrimaryType.DateTimeRfc1123:
                            result = ClassType.DateTimeRfc1123;
                            break;
                        case AutoRestKnownPrimaryType.Double:
                            result = PrimitiveType.Double;
                            break;
                        case AutoRestKnownPrimaryType.Decimal:
                            result = ClassType.BigDecimal;
                            break;
                        case AutoRestKnownPrimaryType.Int:
                            result = PrimitiveType.Int;
                            break;
                        case AutoRestKnownPrimaryType.Long:
                            result = PrimitiveType.Long;
                            break;
                        case AutoRestKnownPrimaryType.Stream:
                            result = GenericType.FlowableByteBuffer;
                            break;
                        case AutoRestKnownPrimaryType.String:
                            if (autoRestPrimaryType.Format.EqualsIgnoreCase(ClassType.URL.Name))
                            {
                                result = ClassType.URL;
                            }
                            else
                            {
                                result = ClassType.String;
                            }
                            break;
                        case AutoRestKnownPrimaryType.TimeSpan:
                            result = ClassType.Duration;
                            break;
                        case AutoRestKnownPrimaryType.UnixTime:
                            result = PrimitiveType.UnixTimeLong;
                            break;
                        case AutoRestKnownPrimaryType.Uuid:
                            result = ClassType.UUID;
                            break;
                        case AutoRestKnownPrimaryType.Object:
                            result = ClassType.Object;
                            break;
                        case AutoRestKnownPrimaryType.Credentials:
                            result = ClassType.ServiceClientCredentials;
                            break;
                        default:
                            throw new NotImplementedException($"Unrecognized AutoRest KnownPrimaryType: {autoRestPrimaryType.KnownPrimaryType}");
                    }
                }
                else
                {
                    throw new ArgumentException($"Unrecognized AutoRest IModelType. Class: {autoRestIModelType.GetType().Name}, Name: {AutoRestIModelTypeName(autoRestIModelType, settings)}");
                }

                parsedAutoRestIModelTypes[autoRestIModelType] = result;
            }
            return result;
        }

        private static IModelTypeJv ParseEnumType(AutoRestEnumType autoRestEnumType, JavaSettings settings)
        {
            string enumTypeName = autoRestEnumType?.Name?.ToString();

            IModelTypeJv enumType;
            if (string.IsNullOrEmpty(enumTypeName) || enumTypeName == "enum")
            {
                enumType = ClassType.String;
            }
            else
            {
                string enumSubpackage = (settings.IsFluent ? "" : settings.ModelsSubpackage);
                string enumPackage = settings.GetSubPackage(enumSubpackage);

                enumTypeName = CodeNamer.Instance.GetTypeName(enumTypeName);

                bool expandable = autoRestEnumType.ModelAsString;

                List<ServiceEnumValue> enumValues = new List<ServiceEnumValue>();
                foreach (AutoRestEnumValue enumValue in autoRestEnumType.Values)
                {
                    enumValues.Add(ParseEnumValue(enumValue.MemberName, enumValue.SerializedName));
                }

                enumType = new EnumType(enumPackage, enumTypeName, expandable, enumValues);
            }

            return enumType;
        }

        internal static ServiceEnumValue ParseEnumValue(string name, string value)
        {
            if (!string.IsNullOrWhiteSpace(name))
            {
                name = enumValueNameRegex.Replace(name, "_");
                for (int i = 1; i < name.Length - 1; i++)
                {
                    if (char.IsUpper(name[i]))
                    {
                        if (name[i - 1] != '_' && char.IsLower(name[i - 1]))
                        {
                            name = name.Insert(i, "_");
                        }
                    }
                }
                name = name.ToUpperInvariant();
            }

            return new ServiceEnumValue(name, value);
        }

        private static bool ShouldParseModelType(AutoRestCompositeType modelType, JavaSettings settings)
        {
            bool shouldParseModelType = false;
            if (modelType != null)
            {
                if (!settings.IsAzure)
                {
                    shouldParseModelType = true;
                }
                else if (!GetExtensionBool(modelType, SwaggerExtensions.ExternalExtension))
                {
                    string modelTypeName = modelType.Name.ToString();
                    if (settings.IsFluent && !string.IsNullOrEmpty(modelTypeName) && innerModelCompositeType.Contains(modelType))
                    {
                        modelTypeName += "Inner";
                    }

                    bool modelTypeIsAzureResourceExtension = GetExtensionBool(modelType, AzureExtensions.AzureResourceExtension);
                    shouldParseModelType = modelTypeName != "Resource" && (modelTypeName != "SubResource" || !modelTypeIsAzureResourceExtension);
                }
            }
            return shouldParseModelType;
        }

        internal static string GetServiceName(Settings autoRestSettings, AutoRestCodeModel codeModel)
            => GetServiceName(autoRestSettings.GetStringSetting("serviceName"), codeModel);

        private static string GetServiceName(string serviceName, AutoRestCodeModel codeModel)
        {
            if (string.IsNullOrEmpty(serviceName))
            {
                AutoRestMethod method = codeModel.Methods.FirstOrDefault();
                Match match = Regex.Match(input: method.Url, pattern: @"/providers/microsoft\.(\w+)/", options: RegexOptions.IgnoreCase);
                serviceName = match.Groups[1].Value.ToPascalCase();
            }
            return serviceName;
        }

        private static bool MethodHasSequenceType(AutoRestIModelType modelType, JavaSettings settings)
        {
            return modelType is AutoRestSequenceType ||
                (modelType is AutoRestCompositeType modelCompositeType &&
                 modelCompositeType.Properties.Any((AutoRestProperty property) => MethodHasSequenceType(property.ModelType, settings)));
        }

        private static void AppendInnerToTopLevelType(AutoRestIModelType type, AutoRestCodeModel serviceClient, JavaSettings settings)
        {
            if (type != null && !removeInner.Contains(type.Name))
            {
                if (type is AutoRestCompositeType compositeType)
                {
                    string compositeTypeName = compositeType.Name.ToString();
                    bool compositeTypeIsAzureResourceExtension = GetExtensionBool(compositeType, AzureExtensions.AzureResourceExtension);
                    if (ModelResourceType(compositeType) == JavaResourceType.None || !compositeTypeIsAzureResourceExtension)
                    {
                        if (!string.IsNullOrEmpty(compositeTypeName) && !innerModelCompositeType.Contains(compositeType))
                        {
                            innerModelCompositeType.Add(compositeType);
                        }

                        foreach (var t in serviceClient.ModelTypes.Where(mt => !innerModelCompositeType.Contains(mt)))
                        {
                            foreach (var p in t.Properties)
                            {
                                if (p.ModelTypeName.EqualsIgnoreCase(compositeType.Name)
                                    || (p.ModelType is AutoRestSequenceType && ((AutoRestSequenceType)p.ModelType).ElementType.Name.EqualsIgnoreCase(compositeType.Name))
                                    || (p.ModelType is AutoRestDictionaryType && ((AutoRestDictionaryType)p.ModelType).ValueType.Name.EqualsIgnoreCase(compositeType.Name)))
                                {
                                    AppendInnerToTopLevelType(t, serviceClient, settings);
                                    break;
                                }
                            }
                        }
                    }

                }
                else if (type is AutoRestSequenceType sequenceType)
                {
                    AppendInnerToTopLevelType(sequenceType.ElementType, serviceClient, settings);
                }
                else if (type is AutoRestDictionaryType dictionaryType)
                {
                    AppendInnerToTopLevelType(dictionaryType.ValueType, serviceClient, settings);
                }
            }
        }
        
        private static void MoveResourceTypeProperties(AutoRestCodeModel codeModel)
        {
            foreach (AutoRestCompositeType subtype in codeModel.ModelTypes.Where(t => ModelResourceType(t.BaseModelType) != JavaResourceType.None))
            {
                var baseType = subtype.BaseModelType as AutoRestCompositeType;
                JavaResourceType baseResourceType = ModelResourceType(baseType);
                if (baseResourceType == JavaResourceType.SubResource)
                {
                    foreach (var prop in baseType.Properties.Where(p => p.SerializedName != "id"))
                    {
                        subtype.Add(prop);
                    }
                }
                else if (baseResourceType == JavaResourceType.ProxyResource)
                {
                    foreach (var prop in baseType.Properties.Where(p => p.SerializedName != "id" && p.SerializedName != "name" && p.SerializedName != "type"))
                    {
                        subtype.Add(prop);
                    }
                    foreach (var prop in baseType.Properties.Where(p => p.SerializedName == "id" || p.SerializedName == "name" || p.SerializedName == "type"))
                    {
                        if (!prop.IsReadOnly)
                        {
                            subtype.Add(prop);
                        }
                    }
                }
                else if (baseResourceType == JavaResourceType.Resource)
                {
                    foreach (var prop in baseType.Properties.Where(p => p.SerializedName != "id" && p.SerializedName != "name" && p.SerializedName != "type" && p.SerializedName != "location" && p.SerializedName != "tags"))
                    {
                        subtype.Add(prop);
                    }
                    foreach (var prop in baseType.Properties.Where(p => p.SerializedName == "id" || p.SerializedName == "name" || p.SerializedName == "type"))
                    {
                        if (!prop.IsReadOnly)
                        {
                            subtype.Add(prop);
                        }
                    }
                    if (!baseType.Properties.First(p => p.SerializedName == "location").IsRequired)
                    {
                        skipParentValidationTypes.Add(subtype);
                    }
                }
            }

            foreach (AutoRestCompositeType subtype in codeModel.ModelTypes.Where(t => t.BaseModelType == null && ModelResourceType(t) == JavaResourceType.None && t.Extensions.ContainsKey(AzureExtensions.AzureResourceExtension)))
            {
                if (subtype.Properties.Any(prop => prop.SerializedName == "id") &&
                    subtype.Properties.Any(prop => prop.SerializedName == "name") &&
                    subtype.Properties.Any(prop => prop.SerializedName == "type"))
                {
                    if (subtype.Properties.Any(prop => prop.SerializedName == "location") &&
                        subtype.Properties.Any(prop => prop.SerializedName == "tags"))
                    {
                        subtype.BaseModelType = JavaResources._resourceType;
                        subtype.Remove(p => p.SerializedName == "location" || p.SerializedName == "tags");
                    }
                    else
                    {
                        subtype.BaseModelType = JavaResources._proxyResourceType;
                    }
                    subtype.Remove(p => p.SerializedName == "id" || p.SerializedName == "name" || p.SerializedName == "type");
                }
            }
        }

        

        /// <summary>
        /// Determines if a model type is a resource base type, and the type of resource it is.
        /// </summary>
        /// <param name="type">the Swagger model type</param>
        /// <returns>the type of the resource, or none if it's not a resource base type</returns>
        private static JavaResourceType ModelResourceType(AutoRestIModelType type)
        {
            if (type is AutoRestCompositeType compositeType)
            {
                if (compositeType.Name.RawValue == "SubResource")
                {
                    return JavaResourceType.SubResource;
                }
                else if (compositeType.Name.RawValue == "TrackedResource")
                {
                    return JavaResourceType.Resource;
                }
                else if (compositeType.Name.RawValue == "ProxyResource")
                {
                    return JavaResourceType.ProxyResource;
                }
                else if (compositeType.Name.RawValue == "Resource")
                {
                    // Make sure location and tags are present, otherwise should be proxy resource
                    var locationProperty = compositeType.Properties.Where(p => p.SerializedName == "location").FirstOrDefault();
                    var tagsProperty = compositeType.Properties.Where(p => p.SerializedName == "tags").FirstOrDefault();
                    if (locationProperty == null || tagsProperty == null)
                    {
                        // Make sure id, name, type are present, otherwise should be sub resource
                        var idProperty = compositeType.Properties.Where(p => p.SerializedName == "id").FirstOrDefault();
                        var nameProperty = compositeType.Properties.Where(p => p.SerializedName == "name").FirstOrDefault();
                        var typeProperty = compositeType.Properties.Where(p => p.SerializedName == "type").FirstOrDefault();
                        if (idProperty == null || nameProperty == null || typeProperty == null)
                        {
                            return JavaResourceType.SubResource;
                        }
                        return JavaResourceType.ProxyResource;
                    }
                    return JavaResourceType.Resource;
                }
            }
            return JavaResourceType.None;
        }

        private static void NormalizeListMethods(List<MethodJv> methods)
        {
            foreach (var method in methods)
            {
                if (method.Name.EqualsIgnoreCase(List) && HasNonClientNonConstantRequiredParameters(method, 1) && method.Parameters.First().Name.StartsWith("resourceGroup"))
                {
                    method.Name.Value = ListByResourceGroup;
                }

                if ((method.Name.EqualsIgnoreCase(ListAll) || method.Name.EqualsIgnoreCase(ListBySubscription))
                    && HasNonClientNonConstantRequiredParameters(method, 0) && !methods.Any(m => m.Name.RawValue == List))
                {
                    method.Name.Value = List;
                }
            }
        }



        private static MethodJv FindGetMethod(IEnumerable<MethodJv> methods)
        {
            MethodJv result = methods.FirstOrDefault(m => GetByResourceGroup.EqualsIgnoreCase(m.Name));
            return result != null && HasNonClientNonConstantRequiredParameters(result, 2) ? result : null;
        }

        private static MethodJv FindDeleteMethod(IEnumerable<MethodJv> methods)
        {
            MethodJv deleteMethod = methods.FirstOrDefault(m => Delete.EqualsIgnoreCase(m.Name));
            return deleteMethod != null && HasNonClientNonConstantRequiredParameters(deleteMethod, 2) ? deleteMethod : null;
        }

        private static bool IsTopLevelResourceUrl(string[] urlSplits)
        {
            return urlSplits.Length == 8 &&
                urlSplits[0].EqualsIgnoreCase("subscriptions") &&
                urlSplits[2].EqualsIgnoreCase("resourceGroups") &&
                urlSplits[4].EqualsIgnoreCase("providers");
        }

        private static MethodJv FindListMethod(IEnumerable<MethodJv> methods)
        {
            MethodJv result = null;

            MethodJv list = methods.FirstOrDefault(m => List.EqualsIgnoreCase(m.Name));
            if (list != null && HasNonClientNonConstantRequiredParameters(list, 0))
            {
                MethodJv listByResourceGroup = methods.FirstOrDefault(m => ListByResourceGroup.EqualsIgnoreCase(m.Name));
                if (listByResourceGroup != null && HasNonClientNonConstantRequiredParameters(listByResourceGroup, 1))
                {
                    string listReturnType = list.AutoRestMethod.ReturnType.Body.ClassName;
                    string listByResourceGroupReturnType = listByResourceGroup.AutoRestMethod.ReturnType.Body.ClassName;
                    if (listReturnType != null && listReturnType.EqualsIgnoreCase(listByResourceGroupReturnType))
                    {
                        result = list;
                    }
                }
            }

            return result;
        }
        private static bool HasNonClientNonConstantRequiredParameters(MethodJv method, int requiredParameterCount)
        {
            // When parameters are optional we generate more methods.
            return method.Parameters.Count(x => !x.IsServiceClientProperty && !x.IsConstant && x.IsRequired) == requiredParameterCount;
        }
        
        private static string GetExtensionString(IDictionary<string, object> extensions, string extensionName)
            => extensions?.GetValue<string>(extensionName);


        private static bool GetExtensionBool(IDictionary<string, object> extensions, string extensionName)
            => extensions?.Get<bool>(extensionName) == true;

        private static bool GetExtensionBool(AutoRestModelType modelType, string extensionName)
            => GetExtensionBool(modelType?.Extensions, extensionName);

        private static string SequenceTypeGetPageImplType(AutoRestIModelType modelType)
            => pageImplTypes.ContainsKey(modelType) ? pageImplTypes[modelType] : null;

        private static void SequenceTypeSetPageImplType(AutoRestIModelType modelType, string pageImplType)
            => pageImplTypes[modelType] = pageImplType;

        private static string AddClientTypePrefix(string clientType, JavaSettings settings)
            => string.IsNullOrEmpty(settings.ClientTypePrefix) ? clientType : settings.ClientTypePrefix + clientType;

        private static IModelTypeJv ConvertToClientType(IModelTypeJv modelType)
        {
            IModelTypeJv clientType = modelType;
            if (modelType is GenericType wireGenericType)
            {
                IModelTypeJv[] wireTypeArguments = wireGenericType.TypeArguments;
                IModelTypeJv[] clientTypeArguments = wireTypeArguments.Select(ConvertToClientType).ToArray();

                for (int i = 0; i < clientTypeArguments.Length; ++i)
                {
                    if (clientTypeArguments[i] != wireTypeArguments[i])
                    {
                        if (wireGenericType is ListType)
                        {
                            clientType = new ListType(clientTypeArguments[0]);
                        }
                        else if (wireGenericType is MapType)
                        {
                            clientType = new MapType(clientTypeArguments[1]);
                        }
                        else
                        {
                            clientType = new GenericType(wireGenericType.Package, wireGenericType.Name, clientTypeArguments);
                        }
                        break;
                    }
                }
            }
            else if (modelType == ClassType.DateTimeRfc1123)
            {
                clientType = ClassType.DateTime;
            }
            else if (modelType == PrimitiveType.UnixTimeLong)
            {
                clientType = ClassType.UnixTimeDateTime;
            }
            else if (modelType == ClassType.Base64Url)
            {
                clientType = ArrayType.ByteArray;
            }
            return clientType;
        }
    }
}
