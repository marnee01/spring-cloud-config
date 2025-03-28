/*
 * Copyright 2013-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.config.server.environment;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

import org.springframework.cloud.config.server.config.ConfigServerProperties;

import static org.springframework.cloud.config.server.environment.AwsClientBuilderConfigurer.configureClientBuilder;

public class AwsS3EnvironmentRepositoryFactory
		implements EnvironmentRepositoryFactory<AwsS3EnvironmentRepository, AwsS3EnvironmentProperties> {

	final private ConfigServerProperties server;

	public AwsS3EnvironmentRepositoryFactory(ConfigServerProperties server) {
		this.server = server;
	}

	@Override
	public AwsS3EnvironmentRepository build(AwsS3EnvironmentProperties environmentProperties) {
		final S3ClientBuilder clientBuilder = S3Client.builder();
		configureClientBuilder(clientBuilder, environmentProperties.getRegion(), environmentProperties.getEndpoint());
		final S3Client client = clientBuilder.build();

		AwsS3EnvironmentRepository repository = new AwsS3EnvironmentRepository(client,
				environmentProperties.getBucket(), environmentProperties.isUseDirectoryLayout(), server);
		repository.setOrder(environmentProperties.getOrder());
		return repository;
	}

}
