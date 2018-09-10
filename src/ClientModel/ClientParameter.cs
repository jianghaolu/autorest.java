// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for license information.

using AutoRest.Core.Model;
using System.Collections.Generic;
using System.Linq;

namespace AutoRest.Java.Model
{
    /// <summary>
    /// A parameter for a method.
    /// </summary>
    public class ClientParameter
    {
        /// <summary>
        /// Create a new Parameter with the provided properties.
        /// </summary>
        /// <param name="description">The description of this parameter.</param>
        /// <param name="isFinal">Whether or not this parameter is final.</param>
        /// <param name="wireType">The wire type of this parameter.</param>
        /// <param name="clientType">The client type of this parameter.</param>
        /// <param name="name">The name of this parameter.</param>
        /// <param name="isRequired">Whether or not this parameter is required.</param>
        /// <param name="annotations">The annotations that should be part of this Parameter's declaration.</param>
        public ClientParameter(string description, bool isFinal, IModelTypeJv wireType, IModelTypeJv clientType, string name, bool isRequired, IEnumerable<ClassType> annotations, Parameter autoRestParameter)
        {
            Description = description;
            IsFinal = isFinal;
            WireType = wireType;
            ClientType = clientType;
            Name = name;
            IsRequired = isRequired;
            Annotations = annotations;
            AutoRestParameter = autoRestParameter;
        }

        /// <summary>
        /// The description of this parameter.
        /// </summary>
        public string Description { get; }

        /// <summary>
        /// Whether or not this parameter is final.
        /// </summary>
        public bool IsFinal { get; }

        /// <summary>
        /// The wire type of this parameter.
        /// </summary>
        public IModelTypeJv WireType { get; }

        /// <summary>
        /// The client type of this parameter.
        /// </summary>
        public IModelTypeJv ClientType { get; }

        /// <summary>
        /// The name of this parameter.
        /// </summary>
        public string Name { get; }

        /// <summary>
        /// Whether or not this parameter is required.
        /// </summary>
        public bool IsRequired { get; }

        /// <summary>
        /// The annotations that should be part of this Parameter's declaration.
        /// </summary>
        public IEnumerable<ClassType> Annotations { get; }

        public Parameter AutoRestParameter { get; }

        /// <summary>
        /// The full declaration of this parameter as it appears in a method signature.
        /// </summary>
        public string Declaration =>
            string.Join("", Annotations.Select((ClassType annotation) => $"@{annotation.Name} ")) +
            (IsFinal ? "final " : "") +
            $"{ClientType} {Name}";

        /// <summary>
        /// Add this parameter's imports to the provided ISet of imports.
        /// </summary>
        /// <param name="imports">The set of imports to add to.</param>
        /// <param name="includeImplementationImports">Whether or not to include imports that are only necessary for method implementations.</param>
        public virtual void AddImportsTo(ISet<string> imports, bool includeImplementationImports)
        {
            foreach (ClassType annotation in Annotations)
            {
                annotation.AddImportsTo(imports, includeImplementationImports);
            }
            ClientType.AddImportsTo(imports, includeImplementationImports);
        }
    }
}
