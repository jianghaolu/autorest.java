// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for license information.

using AutoRest.Core.Utilities;
using System.Collections.Generic;
using System.Linq;
using AutoRestIModelType = AutoRest.Core.Model.IModelType;
using AutoRestKnownPrimaryType = AutoRest.Core.Model.KnownPrimaryType;
using AutoRestMethod = AutoRest.Core.Model.Method;
using AutoRestParameter = AutoRest.Core.Model.Parameter;
using AutoRestParameterLocation = AutoRest.Core.Model.ParameterLocation;
using AutoRestSequenceType = AutoRest.Core.Model.SequenceType;

namespace AutoRest.Java.Model
{
    /// <summary>
    /// A ClientMethod that exists on a ServiceClient or MethodGroupClient that eventually will call a RestAPIMethod.
    /// </summary>
    public class ClientMethod
    {
        /// <summary>
        /// Create a new ClientMethod with the provided properties.
        /// </summary>
        /// <param name="description">The description of this ClientMethod.</param>
        /// <param name="returnValue">The return value of this ClientMethod.</param>
        /// <param name="name">The name of this ClientMethod.</param>
        /// <param name="parameters">The parameters of this ClientMethod.</param>
        /// <param name="onlyRequiredParameters">Whether or not this ClientMethod has omitted optional parameters.</param>
        /// <param name="type">The type of this ClientMethod.</param>
        /// <param name="restAPIMethod">The RestAPIMethod that this ClientMethod eventually calls.</param>
        /// <param name="expressionsToValidate">The expressions (parameters and service client properties) that need to be validated in this ClientMethod.</param>
        /// <param name="pagingNextMethodInfo">The information about the next page method if this is a pageable API.</param>
        /// <param name="serviceCallbackParameter">The async ServiceCallback parameter for this method.</param>
        public ClientMethod(string description, ReturnValue returnValue, string name, IEnumerable<ClientParameter> parameters, bool onlyRequiredParameters, ClientMethodType type, MethodJv restAPIMethod, IEnumerable<string> expressionsToValidate, PagingNextMethodInfo pagingNextMethodInfo, ClientParameter serviceCallbackParameter, List<string> requiredNullableParameterExpressions)
        {
            Description = description;
            ReturnValue = returnValue;
            Name = name;
            Parameters = parameters;
            OnlyRequiredParameters = onlyRequiredParameters;
            Type = type;
            RestAPIMethod = restAPIMethod;
            ExpressionsToValidate = expressionsToValidate;
            PagingNextMethodInfo = pagingNextMethodInfo;
            ServiceCallbackParameter = serviceCallbackParameter;
            RequiredNullableParameterExpressions = requiredNullableParameterExpressions;
        }

        /// <summary>
        /// The description of this ClientMethod.
        /// </summary>
        public string Description { get; }

        /// <summary>
        /// The return value of this ClientMethod.
        /// </summary>
        public ReturnValue ReturnValue { get; }

        /// <summary>
        /// The name of this ClientMethod.
        /// </summary>
        public string Name { get; }

        /// <summary>
        /// The parameters of this ClientMethod.
        /// </summary>
        public IEnumerable<ClientParameter> Parameters { get; }

        /// <summary>
        /// Whether or not this ClientMethod has omitted optional parameters.
        /// </summary>
        public bool OnlyRequiredParameters { get; }

        /// <summary>
        /// The type of this ClientMethod.
        /// </summary>
        public ClientMethodType Type { get; }

        /// <summary>
        /// The RestAPIMethod that this ClientMethod eventually calls.
        /// </summary>
        public MethodJv RestAPIMethod { get; }

        /// <summary>
        /// The expressions (parameters and service client properties) that need to be validated in this ClientMethod.
        /// </summary>
        public IEnumerable<string> ExpressionsToValidate { get; }

        public List<string> RequiredNullableParameterExpressions { get; }

        /// <summary>
        /// The AutoRestMethod that this ClientMethod was created from.
        /// </summary>
        public AutoRestMethod AutoRestMethod => RestAPIMethod.AutoRestMethod;

        public string MethodClientReference => AutoRestMethod.Group.IsNullOrEmpty() ? "this" : "this.client";

        /// <summary>
        /// Get the comma-separated list of parameter declarations for this ClientMethod.
        /// </summary>
        public string ParametersDeclaration => string.Join(", ", Parameters.Select((ClientParameter parameter) => parameter.Declaration));

        /// <summary>
        /// Get the comma-separated list of parameter names for this ClientMethod.
        /// </summary>
        public string ArgumentList => string.Join(", ", Parameters.Select((ClientParameter parameter) => parameter.Name));

        /// <summary>
        /// The full declaration of this ClientMethod.
        /// </summary>
        public string Declaration => $"{ReturnValue.WireType} {Name}({ParametersDeclaration})";

        /// <summary>
        /// The information about the next page method if this represents a pageable API.
        /// </summary>
        public PagingNextMethodInfo PagingNextMethodInfo { get; }

        /// <summary>
        /// The async ServiceCallback parameter for this method.
        /// </summary>
        public ClientParameter ServiceCallbackParameter { get; }

        /// <summary>
        /// Add this ClientMethod's imports to the provided ISet of imports.
        /// </summary>
        /// <param name="imports">The set of imports to add to.</param>
        /// <param name="includeImplementationImports">Whether or not to include imports that are only necessary for method implementations.</param>
        public virtual void AddImportsTo(ISet<string> imports, bool includeImplementationImports, JavaSettings settings)
        {
            ReturnValue.AddImportsTo(imports, includeImplementationImports);

            foreach (ClientParameter parameter in Parameters)
            {
                parameter.AddImportsTo(imports, includeImplementationImports);
            }

            if (includeImplementationImports)
            {
                if (ExpressionsToValidate.Any())
                {
                    imports.Add(ClassType.Validator.FullName);
                }

                if (Type == ClientMethodType.LongRunningAsyncServiceCallback)
                {
                    imports.Add("com.microsoft.azure.v2.util.ServiceFutureUtil");
                }

                List<AutoRestParameter> methodRetrofitParameters = AutoRestMethod.LogicalParameters.Where(p => p.Location != AutoRestParameterLocation.None).ToList();
                if (settings.IsAzureOrFluent && AutoRestMethod.Extensions.Get<bool>("nextLinkMethod") == true)
                {
                    methodRetrofitParameters.RemoveAll(p => p.Location == AutoRestParameterLocation.Path);
                }
                foreach (AutoRestParameter parameter in methodRetrofitParameters)
                {
                    AutoRestParameterLocation location = parameter.Location;
                    AutoRestIModelType parameterModelType = parameter.ModelType;

                    if (location != AutoRestParameterLocation.Body)
                    {
                        if (parameterModelType.IsPrimaryType(AutoRestKnownPrimaryType.ByteArray))
                        {
                            imports.Add("com.microsoft.rest.v2.util.Base64Util");
                        }
                        else if (parameterModelType is AutoRestSequenceType)
                        {
                            imports.Add("com.microsoft.rest.v2.CollectionFormat");
                        }
                    }
                }
            }
        }
    }
}
