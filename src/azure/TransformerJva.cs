// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for license information.
// 

using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using Newtonsoft.Json.Linq;
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
using AutoRest.Core.Utilities;
using AutoRest.Core.Utilities.Collections;
using AutoRest.Extensions.Azure;
using AutoRest.Java.Model;
using static AutoRest.Core.Utilities.DependencyInjection;
using System.Net;
using AutoRest.Core;
using AutoRest.Extensions;

namespace AutoRest.Java.Azure
{
    public class TransformerJva : TransformerJv, ITransformer<AutoRestCodeModel>
    {
        /// <summary>
        /// A type-specific method for code model tranformation.
        /// Note: This is the method you want to override.
        /// </summary>
        /// <param name="codeModel"></param>
        /// <returns></returns>
        public override AutoRestCodeModel TransformCodeModel(AutoRestCodeModel codeModel)
        {
            Settings.Instance.AddCredentials = true;

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
            MoveResourceTypeProperties(codeModel);
            AzureExtensions.AddPageableMethod(codeModel);

            IDictionary<AutoRestIModelType, AutoRestIModelType> convertedTypes = new Dictionary<AutoRestIModelType, AutoRestIModelType>();
            var pageClasses = new List<PageDetails>();

            foreach (AutoRestMethod autoRestMethod in codeModel.Methods)
            {
                bool simulateMethodAsPagingOperation = false;
                AutoRestMethodGroup methodGroup = autoRestMethod.MethodGroup;
                if (!string.IsNullOrEmpty(methodGroup?.Name?.ToString()))
                {
                    ServiceMethodType restAPIMethodType = ServiceMethodType.Other;
                    string methodUrl = methodTypeTrailing.Replace(methodTypeLeading.Replace(autoRestMethod.Url, ""), "");
                    string[] urlSplits = methodUrl.Split('/');
                    switch (autoRestMethod.HttpMethod)
                    {
                        case AutoRestHttpMethod.Get:
                            if ((urlSplits.Length == 5 || urlSplits.Length == 7)
                                && urlSplits[0].EqualsIgnoreCase("subscriptions")
                                && MethodHasSequenceType(autoRestMethod.ReturnType.Body, settings))
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

                bool methodHasPageableExtensions = autoRestMethod.Extensions.ContainsKey(AzureExtensions.PageableExtension);
                JContainer methodPageableExtensions = !methodHasPageableExtensions ? null : autoRestMethod.Extensions[AzureExtensions.PageableExtension] as JContainer;
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
                                autoRestMethod.Extensions[AzureExtensions.PageableExtension] = null;
                            }

                            bool anyTypeConverted = false;
                            foreach (HttpStatusCode responseStatus in autoRestMethod.Responses.Where(r => r.Value.Body is AutoRestCompositeType).Select(s => s.Key).ToArray())
                            {
                                anyTypeConverted = true;
                                AutoRestCompositeType compositeType = (AutoRestCompositeType)autoRestMethod.Responses[responseStatus].Body;
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

                                    convertedTypes[autoRestMethod.Responses[responseStatus].Body] = pagedResult;
                                    AutoRestResponse resp = DependencyInjection.New<AutoRestResponse>(pagedResult, autoRestMethod.Responses[responseStatus].Headers);
                                    autoRestMethod.Responses[responseStatus] = resp;
                                }
                            }

                            if (!anyTypeConverted && simulateMethodAsPagingOperation)
                            {
                                foreach (HttpStatusCode responseStatus in autoRestMethod.Responses.Where(r => r.Value.Body is AutoRestSequenceType).Select(s => s.Key).ToArray())
                                {
                                    AutoRestSequenceType sequenceType = (AutoRestSequenceType)autoRestMethod.Responses[responseStatus].Body;

                                    AutoRestSequenceType pagedResult = DependencyInjection.New<AutoRestSequenceType>();
                                    pagedResult.ElementType = sequenceType.ElementType;
                                    SequenceTypeSetPageImplType(pagedResult, pageDetails.ClassName);

                                    convertedTypes[autoRestMethod.Responses[responseStatus].Body] = pagedResult;
                                    AutoRestResponse resp = DependencyInjection.New<AutoRestResponse>(pagedResult, autoRestMethod.Responses[responseStatus].Headers);
                                    autoRestMethod.Responses[responseStatus] = resp;
                                }
                            }

                            if (convertedTypes.ContainsKey(autoRestMethod.ReturnType.Body))
                            {
                                AutoRestResponse resp = DependencyInjection.New<AutoRestResponse>(convertedTypes[autoRestMethod.ReturnType.Body], autoRestMethod.ReturnType.Headers);
                                autoRestMethod.ReturnType = resp;
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

            return codeModel;
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
    }
}