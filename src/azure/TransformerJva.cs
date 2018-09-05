// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for license information.
// 

using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using Newtonsoft.Json.Linq;
using AutoRest.Core;
using AutoRest.Core.Model;
using AutoRest.Core.Utilities;
using AutoRest.Core.Utilities.Collections;
using AutoRest.Extensions;
using AutoRest.Extensions.Azure;
using AutoRest.Java.Model;
using static AutoRest.Core.Utilities.DependencyInjection;
using System.Net;

namespace AutoRest.Java.Azure
{
    public class TransformerJva : TransformerJv, ITransformer<CodeModel>
    {
        /// <summary>
        /// A type-specific method for code model tranformation.
        /// Note: This is the method you want to override.
        /// </summary>
        /// <param name="codeModel"></param>
        /// <returns></returns>
        public override CodeModel TransformCodeModel(CodeModel codeModel)
        {
            var settings = Settings.Instance;

            settings.AddCredentials = true;

            // This extension from general extensions must be run prior to Azure specific extensions.
            SwaggerExtensions.ProcessParameterizedHost(codeModel);
            AzureExtensions.ProcessClientRequestIdExtension(codeModel);
            AzureExtensions.UpdateHeadMethods(codeModel);
            SwaggerExtensions.ProcessGlobalParameters(codeModel);
            SwaggerExtensions.FlattenModels(codeModel);
            SwaggerExtensions.FlattenMethodParameters(codeModel);
            ParameterGroupExtensionHelper.AddParameterGroups(codeModel);

            foreach (MethodGroup methodGroup in codeModel.Operations)
            {
                Method[] methods = methodGroup.Methods.ToArray();
                methodGroup.ClearMethods();
                foreach (Method method in methods)
                {
                    methodGroup.Add(method);
                    if (GetExtensionBool(method.Extensions, AzureExtensions.LongRunningExtension))
                    {
                        Response response = method.Responses.Values.First();
                        if (!method.Responses.ContainsKey(HttpStatusCode.OK))
                        {
                            method.Responses[HttpStatusCode.OK] = response;
                        }
                        if (!method.Responses.ContainsKey(HttpStatusCode.Accepted))
                        {
                            method.Responses[HttpStatusCode.Accepted] = response;
                        }
                        if (method.HttpMethod != HttpMethod.Get && !method.Responses.ContainsKey(HttpStatusCode.NoContent))
                        {
                            method.Responses[HttpStatusCode.NoContent] = response;
                        }

                        Method m = DependencyInjection.Duplicate(method);
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

            IDictionary<IModelType, IModelType> convertedTypes = new Dictionary<IModelType, IModelType>();

            foreach (Method restAPIMethod in codeModel.Methods)
            {
                bool simulateMethodAsPagingOperation = false;
                MethodGroup methodGroup = restAPIMethod.MethodGroup;
                if (!string.IsNullOrEmpty(methodGroup?.Name?.ToString()))
                {
                    ServiceMethodType restAPIMethodType = ServiceMethodType.Other;
                    string methodUrl = methodTypeTrailing.Replace(methodTypeLeading.Replace(restAPIMethod.Url, ""), "");
                    string[] urlSplits = methodUrl.Split('/');
                    switch (restAPIMethod.HttpMethod)
                    {
                        case HttpMethod.Get:
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

                        case HttpMethod.Delete:
                            if (IsTopLevelResourceUrl(urlSplits))
                            {
                                restAPIMethodType = ServiceMethodType.Delete;
                            }
                            break;
                    }

                    simulateMethodAsPagingOperation = (restAPIMethodType == ServiceMethodType.ListByResourceGroup || restAPIMethodType == ServiceMethodType.ListBySubscription) &&
                        1 == methodGroup.Methods.Count((Method methodGroupMethod) =>
                        {
                            ServiceMethodType methodGroupMethodType = ServiceMethodType.Other;
                            string methodGroupMethodUrl = methodTypeTrailing.Replace(methodTypeLeading.Replace(methodGroupMethod.Url, ""), "");
                            string[] methodGroupUrlSplits = methodGroupMethodUrl.Split('/');
                            switch (methodGroupMethod.HttpMethod)
                            {
                                case HttpMethod.Get:
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

                                case HttpMethod.Delete:
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
                            foreach (HttpStatusCode responseStatus in restAPIMethod.Responses.Where(r => r.Value.Body is CompositeType).Select(s => s.Key).ToArray())
                            {
                                anyTypeConverted = true;
                                CompositeType compositeType = (CompositeType)restAPIMethod.Responses[responseStatus].Body;
                                SequenceType sequenceType = compositeType.Properties
                                    .Select((AutoRestProperty property) =>
                                    {
                                        IModelType propertyModelType = property.ModelType;
                                        if (propertyModelType != null && !IsNullable(property) && propertyModelType is PrimaryType propertyModelPrimaryType)
                                        {
                                            PrimaryType propertyModelNonNullablePrimaryType = DependencyInjection.New<PrimaryType>(propertyModelPrimaryType.KnownPrimaryType);
                                            propertyModelNonNullablePrimaryType.Format = propertyModelPrimaryType.Format;
                                            primaryTypeNotWantNullable.Add(propertyModelNonNullablePrimaryType);

                                            propertyModelType = propertyModelNonNullablePrimaryType;
                                        }
                                        return propertyModelType;
                                    })
                                    .FirstOrDefault(t => t is SequenceType) as SequenceType;

                                // if the type is a wrapper over page-able response
                                if (sequenceType != null)
                                {
                                    SequenceType pagedResult = DependencyInjection.New<SequenceType>();
                                    pagedResult.ElementType = sequenceType.ElementType;
                                    SequenceTypeSetPageImplType(pagedResult, pageDetails.ClassName);

                                    convertedTypes[restAPIMethod.Responses[responseStatus].Body] = pagedResult;
                                    Response resp = DependencyInjection.New<Response>(pagedResult, restAPIMethod.Responses[responseStatus].Headers);
                                    restAPIMethod.Responses[responseStatus] = resp;
                                }
                            }

                            if (!anyTypeConverted && simulateMethodAsPagingOperation)
                            {
                                foreach (HttpStatusCode responseStatus in restAPIMethod.Responses.Where(r => r.Value.Body is SequenceType).Select(s => s.Key).ToArray())
                                {
                                    SequenceType sequenceType = (SequenceType)restAPIMethod.Responses[responseStatus].Body;

                                    SequenceType pagedResult = DependencyInjection.New<SequenceType>();
                                    pagedResult.ElementType = sequenceType.ElementType;
                                    SequenceTypeSetPageImplType(pagedResult, pageDetails.ClassName);

                                    convertedTypes[restAPIMethod.Responses[responseStatus].Body] = pagedResult;
                                    Response resp = DependencyInjection.New<Response>(pagedResult, restAPIMethod.Responses[responseStatus].Headers);
                                    restAPIMethod.Responses[responseStatus] = resp;
                                }
                            }

                            if (convertedTypes.ContainsKey(restAPIMethod.ReturnType.Body))
                            {
                                Response resp = DependencyInjection.New<Response>(convertedTypes[restAPIMethod.ReturnType.Body], restAPIMethod.ReturnType.Headers);
                                restAPIMethod.ReturnType = resp;
                            }
                        }
                    }
                }
            }

            SwaggerExtensions.RemoveUnreferencedTypes(codeModel,
                new HashSet<string>(convertedTypes.Keys
                    .Where(x => x is CompositeType)
                    .Cast<CompositeType>()
                    .Select((CompositeType compositeType) =>
                    {
                        string compositeTypeName = compositeType.Name.ToString();
                        if (settings.IsFluent && !string.IsNullOrEmpty(compositeTypeName) && innerModelCompositeType.Contains(compositeType))
                        {
                            compositeTypeName += "Inner";
                        }
                        return compositeTypeName;
                    })));
        }
    }
}
