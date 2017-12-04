/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ambari.server.topology.tasks;

import java.util.List;
import java.util.Map;

import org.apache.ambari.server.topology.ClusterTopology;
import org.apache.ambari.server.topology.ComponentV2;
import org.apache.ambari.server.topology.HostGroupV2;
import org.apache.ambari.server.topology.HostRequest;
import org.apache.ambari.server.topology.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class PersistHostResourcesTask extends TopologyHostTask  {

  private final static Logger LOG = LoggerFactory.getLogger(PersistHostResourcesTask.class);

  @AssistedInject
  public PersistHostResourcesTask(@Assisted ClusterTopology topology, @Assisted HostRequest hostRequest) {
    super(topology, hostRequest);
  }

  @Override
  public Type getType() {
    return Type.RESOURCE_CREATION;
  }

  @Override
  public void runTask() {
    LOG.info("HostRequest: Executing RESOURCE_CREATION task for host: {}", hostRequest.getHostName());

    HostGroupV2 group = hostRequest.getHostGroup();
    Map<Service, List<ComponentV2>> serviceComponents = group.getComponentsByService();
    clusterTopology.getAmbariContext().createAmbariHostResources(hostRequest.getClusterId(), hostRequest.getHostName(), serviceComponents);

    LOG.info("HostRequest: Exiting RESOURCE_CREATION task for host: {}", hostRequest.getHostName());
  }
}
