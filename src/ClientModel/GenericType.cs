// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for license information.

using System.Collections.Generic;
using System.Linq;

namespace AutoRest.Java.Model
{
    /// <summary>
    /// A generic type that is used by the service.
    /// </summary>
    public class GenericType : IModelTypeJv
    {
        public static GenericType ServiceCallback(IModelTypeJv typeArgument) => new GenericType("com.microsoft.rest.v2", "ServiceCallback", typeArgument);
        public static GenericType ServiceFuture(IModelTypeJv typeArgument) => new GenericType("com.microsoft.rest.v2", "ServiceFuture", typeArgument);
        public static GenericType Flowable(IModelTypeJv typeArgument) => new GenericType("io.reactivex", "Flowable", typeArgument);
        public static GenericType Maybe(IModelTypeJv typeArgument) => new GenericType("io.reactivex", "Maybe", typeArgument);
        public static GenericType Observable(IModelTypeJv typeArgument) => new GenericType("io.reactivex", "Observable", typeArgument);
        public static GenericType OperationStatus(IModelTypeJv typeArgument) => new GenericType("com.microsoft.azure.v2", "OperationStatus", typeArgument);
        public static GenericType Page(IModelTypeJv elementType) => new GenericType("com.microsoft.azure.v2", "Page", elementType);
        public static GenericType PagedList(IModelTypeJv elementType) => new GenericType("com.microsoft.azure.v2", "PagedList", elementType);
        public static GenericType RestResponse(IModelTypeJv headersType, IModelTypeJv bodyType) => new GenericType("com.microsoft.rest.v2", "RestResponse", headersType, bodyType);
        public static GenericType BodyResponse(IModelTypeJv bodyType) => new GenericType("com.microsoft.rest.v2", "BodyResponse", bodyType);
        public static GenericType Single(IModelTypeJv typeArgument) => new GenericType("io.reactivex", "Single", typeArgument);
        public static GenericType Function(IModelTypeJv inputType, IModelTypeJv outputType) => new GenericType("io.reactivex.functions", "Function", inputType, outputType);

        public static readonly GenericType FlowableByteBuffer = Flowable(ClassType.ByteBuffer);

        /// <summary>
        /// Create a new GenericType from the provided properties.
        /// </summary>
        /// <param name="name">The main non-generic type of this generic type.</param>
        /// <param name="typeArguments">The type arguments of this generic type.</param>
        internal GenericType(string package, string name, params IModelTypeJv[] typeArguments)
        {
            Name = name;
            Package = package;
            TypeArguments = typeArguments;
        }

        /// <summary>
        /// The main non-generic type of this generic type.
        /// </summary>
        public string Name { get; }

        /// <summary>
        /// The package that this type belongs to.
        /// </summary>
        public string Package { get; }

        /// <summary>
        /// The type arguments of this generic type.
        /// </summary>
        public IModelTypeJv[] TypeArguments { get; }

        public override string ToString()
        {
            return $"{Name}<{string.Join(", ", TypeArguments.Select((IModelTypeJv typeArgument) => typeArgument.AsNullable().ToString()))}>";
        }

        public override bool Equals(object rhs)
        {
            return rhs is GenericType genericTypeRhs &&
                Package == genericTypeRhs.Package &&
                Name == genericTypeRhs.Name &&
                TypeArguments.SequenceEqual(genericTypeRhs.TypeArguments);
        }

        public override int GetHashCode()
        {
            return Package.GetHashCode() +
                Name.GetHashCode() +
                TypeArguments.Select(typeArgument => typeArgument.GetHashCode()).Sum();
        }

        public IModelTypeJv AsNullable()
        {
            return this;
        }

        public bool Contains(IModelTypeJv type)
        {
            return this == type || TypeArguments.Any((IModelTypeJv typeArgument) => typeArgument.Contains(type));
        }

        public virtual void AddImportsTo(ISet<string> imports, bool includeImplementationImports)
        {
            imports.Add($"{Package}.{Name}");
            foreach (IModelTypeJv typeArgument in TypeArguments)
            {
                typeArgument.AddImportsTo(imports, includeImplementationImports);
            }
        }

        public string DefaultValueExpression(string sourceExpression)
        {
            return sourceExpression;
        }
    }
}
