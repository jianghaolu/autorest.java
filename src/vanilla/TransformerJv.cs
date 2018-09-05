// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for license information.
// 

using AutoRest.Core;
using AutoRest.Core.Model;
using AutoRest.Java.Model;
using AutoRest.Extensions;
using static AutoRest.Core.Utilities.DependencyInjection;

namespace AutoRest.Java
{
    public class TransformerJv : CodeModelTransformer<CodeModel>
    {
        public override CodeModel TransformCodeModel(CodeModel cs)
        {
            SwaggerExtensions.NormalizeClientModel(cs);

            return cs;
        }
    }
}