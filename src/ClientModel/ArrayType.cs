﻿// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for license information.

using System;
using System.Collections.Generic;

namespace AutoRest.Java.Model
{
    public class ArrayType : IType
    {
        public static readonly ArrayType ByteArray = new ArrayType(PrimitiveType.Byte, (string defaultValueExpression) => defaultValueExpression == null ? "new byte[0]" : $"\"{defaultValueExpression}\".getBytes()");

        private ArrayType(IType elementType, Func<string,string> defaultValueExpressionConverter)
        {
            ElementType = elementType;
            DefaultValueExpressionConverter = defaultValueExpressionConverter;
        }

        public IType ElementType { get; }

        private Func<string, string> DefaultValueExpressionConverter { get; }

        public override string ToString()
        {
            return $"{ElementType}[]";
        }
        
        public IType AsNullable()
        {
            return this;
        }

        public bool Contains(IType type)
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

        public IType ConvertToClientType()
        {
            var result = this;
            var elementClientType = ElementType.ConvertToClientType();
            if (elementClientType != ElementType)
            {
                result = new ArrayType(elementClientType, DefaultValueExpressionConverter);
            }
            return result;
        }
    }
}