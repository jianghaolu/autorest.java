// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for license information.

using System;

namespace AutoRest.Java.Model
{
    /// <summary>
    /// The details needed to create a Manager class for the service.
    /// </summary>
    public class ClientManager
    {
        /// <summary>
        /// Create a new ServiceManager with the provided properties.
        /// </summary>
        /// <param name="serviceClientName">The name of the service client.</param>
        /// <param name="serviceName">The name of the service.</param>
        /// <param name="httpPipelineParameter">The HTTP pipeline for the manager.</param>
        public ClientManager(string serviceClientName, string serviceName, Lazy<ClientParameter> azureTokenCredentialsParameter, Lazy<ClientParameter> httpPipelineParameter)
        {
            ServiceClientName = serviceClientName;
            ServiceName = serviceName;
            AzureTokenCredentialsParameter = azureTokenCredentialsParameter;
            HttpPipelineParameter = httpPipelineParameter;
        }

        /// <summary>
        /// The name of the service client.
        /// </summary>
        public string ServiceClientName { get; }

        /// <summary>
        /// The name of the service.
        /// </summary>
        public string ServiceName { get; }

        /// <summary>
        /// The <code>AzureTokenCredentials</code> parameter for the manager.
        /// </summary>
        public Lazy<ClientParameter> AzureTokenCredentialsParameter { get; }

        /// <summary>
        /// The HTTP pipeline for the manager.
        /// </summary>
        public Lazy<ClientParameter> HttpPipelineParameter { get; }
    }
}
