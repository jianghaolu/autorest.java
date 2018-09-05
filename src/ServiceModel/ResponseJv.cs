// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for license information.

namespace AutoRest.Java.Model
{
    public sealed class ResponseJv
    {
        public string Name { get; }
        public string Package { get; }
        public string Description { get; }
        public IModelTypeJv Headers { get; }
        public IModelTypeJv Body { get; }

        public ResponseJv(string name, string package, string description, IModelTypeJv headersType, IModelTypeJv bodyType)
        {
            Name = name;
            Package = package;
            Description = description;
            Headers = headersType;
            Body = bodyType;
        }
    }
}