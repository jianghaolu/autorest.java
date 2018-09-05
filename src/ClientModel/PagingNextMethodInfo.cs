// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for license information.

using AutoRest.Core.Model;
using System;
using System.Collections.Generic;
using System.Text;

namespace AutoRest.Java.Model
{
    public class PagingNextMethodInfo
    {
        public PagingNextMethodInfo(Method nextMethod, IModelTypeJv pageType, string nextMethodInvocation, string nextPageLinkParameterName, string nextPageLinkVariableName, string nextGroupTypeName, string groupedTypeName, Parameter groupedType, string nextMethodParameterInvocation)
        {
            this.NextMethod = nextMethod;
            this.PageType = pageType;
            this.NextMethodInvocation = nextMethodInvocation;
            this.NextPageLinkParameterName = nextPageLinkParameterName;
            this.NextPageLinkVariableName = nextPageLinkVariableName;
            this.NextGroupTypeName = nextGroupTypeName;
            this.GroupedTypeName = groupedTypeName;
            this.GroupedType = groupedType;
            this.NextMethodParameterInvocation = nextMethodParameterInvocation;
        }

        public Method NextMethod { get; }

        public IModelTypeJv PageType { get; }

        public string NextMethodInvocation { get; }

        public string NextPageLinkParameterName { get; }

        public string NextPageLinkVariableName { get; }

        public string NextGroupTypeName { get; }

        public string GroupedTypeName { get; }

        public Parameter GroupedType { get; }

        public string NextMethodParameterInvocation { get; }

    }
}
