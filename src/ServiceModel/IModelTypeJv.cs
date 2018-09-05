﻿// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for license information.

using System.Collections.Generic;

namespace AutoRest.Java.Model
{
    /// <summary>
    /// A type used by a service.
    /// </summary>
    public interface IModelTypeJv
    {
        /// <summary>
        /// Convert this IType to an IType that is nullable.
        /// </summary>
        /// <returns>A version of this IType that is nullable.</returns>
        IModelTypeJv AsNullable();

        /// <summary>
        /// Get whether or not this IType contains (or is) the provided type.
        /// </summary>
        /// <param name="type">The type to search for.</param>
        /// <returns>Whether or not this IType contains (or is) the provided type.</returns>
        bool Contains(IModelTypeJv type);

        /// <summary>
        /// Add this type's imports to the provided ISet of imports.
        /// </summary>
        /// <param name="imports">The set of imports to add to.</param>
        /// <param name="includeImplementationImports">Whether or not to include imports that are only necessary for method implementations.</param>
        void AddImportsTo(ISet<string> imports, bool includeImplementationImports);

        /// <summary>
        /// Convert the provided default value expression to this type's default value expression.
        /// </summary>
        /// <param name="sourceExpression">The source expression to convert to this type's default value expression.</param>
        /// <returns>This type's default value expression.</returns>
        string DefaultValueExpression(string sourceExpression);
    }
}
