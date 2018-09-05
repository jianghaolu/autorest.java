// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for license information.

using System.Collections.Generic;

namespace AutoRest.Java.Model
{
    /// <summary>
    /// A return value from a method.
    /// </summary>
    public class ReturnValue
    {
        /// <summary>
        /// Create a new ReturnValue object from the provided properties.
        /// </summary>
        /// <param name="description">The description of the return value.</param>
        /// <param name="wireType">The type of the return value sent on the wire.</param>
        /// <param name="clientType">The type of the return value users interact with.</param>
        public ReturnValue(string description, IModelTypeJv wireType, IModelTypeJv clientType)
        {
            Description = description;
            WireType = wireType;
            ClientType = clientType;
        }

        /// <summary>
        /// The description of the return value.
        /// </summary>
        public string Description { get; }

        /// <summary>
        /// The type of the return value sent on the wire.
        /// </summary>
        public IModelTypeJv WireType { get; }

        /// <summary>
        /// The type of the return value users interact with.
        /// </summary>
        public IModelTypeJv ClientType { get; }

        /// <summary>
        /// Add this return value's imports to the provided ISet of imports.
        /// </summary>
        /// <param name="imports">The set of imports to add to.</param>
        /// <param name="includeImplementationImports">Whether or not to include imports that are only necessary for method implementations.</param>
        public void AddImportsTo(ISet<string> imports, bool includeImplementationImports)
        {
            WireType.AddImportsTo(imports, includeImplementationImports);
            ClientType.AddImportsTo(imports, includeImplementationImports);
        }
    }
}
