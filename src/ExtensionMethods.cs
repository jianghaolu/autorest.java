// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for license information.

using AutoRest.Core;
using AutoRest.Core.Model;
using AutoRest.Core.Utilities;
using System;
using System.Text;

namespace AutoRest.Java
{
    internal static class ExtensionMethods
    {
        internal static string TrimMultilineHeader(this string header)
        {
            if (string.IsNullOrEmpty(header))
            {
                return header;
            }
            StringBuilder builder = new StringBuilder();
            foreach (string headerLine in header.Split(new string[] { Environment.NewLine }, StringSplitOptions.None))
            {
                builder.Append(headerLine.TrimEnd()).Append(Environment.NewLine);
            }
            return builder.ToString();
        }

        internal static bool IsNullable(this IVariable variable)
            => variable.IsXNullable.HasValue ? variable.IsXNullable.Value : !variable.IsRequired;

        internal static string GetStringSetting(this Settings autoRestSettings, string settingName, string defaultValue = null)
        {
            return autoRestSettings.Host.GetValue(settingName).Result ?? defaultValue;
        }

        internal static bool GetBoolSetting(this Settings autoRestSettings, string settingName, bool defaultValue = false)
        {
            bool customSettingValue = defaultValue;

            string settingValueString = autoRestSettings.GetStringSetting(settingName, null);
            if (bool.TryParse(settingValueString, out bool settingValueBool))
            {
                customSettingValue = settingValueBool;
            }

            return customSettingValue;
        }

        internal static string Async(this Fixable<string> methodName)
        {
            return Async(methodName.Value);
        }

        internal static string WithRestResponse(this Fixable<string> methodName)
        {
            return WithRestResponse(methodName.Value);
        }

        internal static string Async(this string methodName)
        {
            if (methodName == null)
            {
                throw new NullReferenceException("methodName == null");
            }
            if (methodName.EndsWith("Async"))
            {
                return methodName;
            } else
            {
                return methodName + "Async";
            }
        }

        internal static string WithRestResponse(this string methodName)
        {
            if (methodName == null)
            {
                throw new NullReferenceException("methodName == null");
            }
            if (methodName.EndsWith("WithRestResponse"))
            {
                return methodName;
            }
            else
            {
                return methodName + "WithRestResponse";
            }
        }
    }
}
