// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for license information.

using System.Collections.Generic;

namespace AutoRest.Java.Model
{
    /// <summary>
    /// A container for the types associated for accessing a specific service.
    /// </summary>
    public class Client
    {
        /// <summary>
        /// Create a new Service with the provided values.
        /// </summary>
        /// <param name="clientName">The name of the service client.</param>
        /// <param name="clientDescription">The description of the service client.</param>
        /// <param name="enums">The enum types that are used by the service.</param>
        /// <param name="exceptions">The exception types that are used by the service.</param>
        /// <param name="xmlSequenceWrappers"></param>
        /// <param name="models"></param>
        /// <param name="pageClasses"></param>
        /// <param name="manager"></param>
        /// <param name="serviceClient"></param>
        public Client(
            string clientName,
            string clientDescription,
            IEnumerable<EnumType> enums,
            IEnumerable<ClientException> exceptions,
            IEnumerable<XmlSequenceWrapper> xmlSequenceWrappers,
            IEnumerable<ResponseJv> responseModels,
            IEnumerable<ClientModel> models,
            IEnumerable<PageDetails> pageClasses,
            ClientManager manager,
            ServiceClient serviceClient)
        {
            Name = clientName;
            Description = clientDescription;
            Enums = enums;
            Exceptions = exceptions;
            XmlSequenceWrappers = xmlSequenceWrappers;
            ResponseModels = responseModels;
            Models = models;
            PageClasses = PageClasses;
            Manager = manager;
            ServiceClient = serviceClient;
        }

        /// <summary>
        /// The name of this service client.
        /// </summary>
        public string Name { get; }

        /// <summary>
        /// The description of this service.
        /// </summary>
        public string Description { get; }

        /// <summary>
        /// Get the enum types that are used by this service.
        /// </summary>
        public IEnumerable<EnumType> Enums { get; }

        /// <summary>
        /// Get the exception types that are used by this service.
        /// </summary>
        public IEnumerable<ClientException> Exceptions { get; }

        /// <summary>
        /// Get the XML sequence wrappers that are used by this service.
        /// </summary>
        public IEnumerable<XmlSequenceWrapper> XmlSequenceWrappers { get; }

        /// <summary>
        /// Get the response models which contain the response status code, headers and body for each service method.
        /// </summary>
        public IEnumerable<ResponseJv> ResponseModels { get; }

        /// <summary>
        /// Get the model types that are used by this service.
        /// </summary>
        public IEnumerable<ClientModel> Models { get; }

        /// <summary>
        /// Get the page classes that are used by this service.
        /// </summary>
        public IEnumerable<PageDetails> PageClasses { get; }

        /// <summary>
        /// Get the Manager for this service.
        /// </summary>
        public ClientManager Manager { get; }

        /// <summary>
        /// The ServiceClient for this service.
        /// </summary>
        public ServiceClient ServiceClient { get; }
    }
}
