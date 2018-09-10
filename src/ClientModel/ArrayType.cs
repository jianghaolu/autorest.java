// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for license information.

using System;
using System.Collections.Generic;

namespace AutoRest.Java.Model
{
    public class ArrayType : IModelTypeJv
    {
        public static readonly ArrayType ByteArray = new ArrayType(PrimitiveType.Byte, (string defaultValueExpression) => defaultValueExpression == null ? "new byte[0]" : $"\"{defaultValueExpression}\".getBytes()");

        private ArrayType(IModelTypeJv elementType, Func<string,string> defaultValueExpressionConverter)
        {
            ElementType = elementType;
            DefaultValueExpressionConverter = defaultValueExpressionConverter;
        }

        public IModelTypeJv ElementType { get; }

        private Func<string, string> DefaultValueExpressionConverter { get; }

        public string Name => throw new NotImplementedException();

        public override string ToString()
        {
            return $"{ElementType}[]";
        }
        
        public IModelTypeJv AsNullable()
        {
            return this;
        }

        public bool Contains(IModelTypeJv type)
        {
            return this == type || ElementType.Contains(type);
        }

        public void AddImportsTo(ISet<string> imports, bool includeImplementationImports)
        {
            ElementType.AddImportsTo(imports, includeImplementationImports);
        }

        public string DefaultValueExpression(string sourceExpression)
        {
            return DefaultValueExpressionConverter(sourceExpression);
        }

        public bool StructurallyEquals(IModelTypeJv type)
        {
            return 
        }
    }
}
