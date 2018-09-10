// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for license information.

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using static AutoRest.Core.Utilities.DependencyInjection;

namespace AutoRest.Java.Model
{
    /// <summary>
    /// Defines a parameter transformation.
    /// </summary>
    public class ParameterTransformationJv
    {
        public ParameterTransformationJv(ParameterJv outputParameter, List<ParameterMappingJv> parameterMappings)
        {
            ParameterMappings = new List<ParameterMappingJv>();
        }
        /// <summary>
        /// Gets or sets the output parameter.
        /// </summary>
        public ParameterJv OutputParameter { get; set; }

        /// <summary>
        /// Gets the list of Parameter Mappings
        /// </summary>
        public List<ParameterMappingJv> ParameterMappings { get; private set; }

    }
}