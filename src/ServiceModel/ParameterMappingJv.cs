// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for license information.

using AutoRest.Core.Model;
using System;
using System.Globalization;
using static AutoRest.Core.Utilities.DependencyInjection;

namespace AutoRest.Java.Model
{
    /// <summary>
    /// Defines a parameter mapping.
    /// </summary>
    public class ParameterMappingJv
    {
        public ParameterMappingJv(ParameterJv inputParameter, string inputParameterProperty, string outputParameterProperty)
        {
            this.InputParameter = inputParameter;
            this.InputParameterProperty = inputParameterProperty;
            this.OutputParameterProperty = outputParameterProperty;
        }

        /// <summary>
        /// Gets or sets the input parameter.
        /// </summary>
        public ParameterJv InputParameter { get; set; }

        /// <summary>
        /// Gets or sets the input parameter dot separated property path.
        /// </summary>
        public string InputParameterProperty { get; set; }

        /// <summary>
        /// Gets or sets the output parameter dot separated property path.
        /// </summary>
        public string OutputParameterProperty { get; set; }
    }
}