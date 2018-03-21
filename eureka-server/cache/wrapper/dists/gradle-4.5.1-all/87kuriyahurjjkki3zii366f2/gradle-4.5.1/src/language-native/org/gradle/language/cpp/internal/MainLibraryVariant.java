/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.language.cpp.internal;

import com.google.common.collect.ImmutableSet;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.PublishArtifact;
import org.gradle.api.attributes.Usage;
import org.gradle.api.component.ComponentWithVariants;
import org.gradle.api.component.SoftwareComponent;
import org.gradle.api.internal.component.SoftwareComponentInternal;
import org.gradle.api.internal.component.UsageContext;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class MainLibraryVariant implements ComponentWithVariants, SoftwareComponentInternal {
    private final Set<SoftwareComponent> variants = new HashSet<SoftwareComponent>();
    private final String name;
    private final Usage usage;
    private final Set<PublishArtifact> artifacts = new LinkedHashSet<PublishArtifact>();
    private final Configuration dependencies;

    public MainLibraryVariant(String name, Usage usage, Configuration dependencies) {
        this.name = name;
        this.usage = usage;
        this.dependencies = dependencies;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Set<? extends UsageContext> getUsages() {
        return ImmutableSet.of(new DefaultUsageContext(name, usage, artifacts, dependencies));
    }

    @Override
    public Set<? extends SoftwareComponent> getVariants() {
        return variants;
    }

    public void addArtifact(PublishArtifact artifact) {
        artifacts.add(artifact);
    }

    /**
     * Adds a child variant
     */
    public void addVariant(SoftwareComponent variant) {
        variants.add(variant);
    }
}