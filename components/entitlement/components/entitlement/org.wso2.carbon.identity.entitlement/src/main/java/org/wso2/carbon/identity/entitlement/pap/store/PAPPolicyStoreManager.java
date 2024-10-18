/*
*  Copyright (c) 2005-2024, WSO2 LLC (https://www.wso2.com) All Rights Reserved.
*
*  WSO2 LLC licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.carbon.identity.entitlement.pap.store;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.entitlement.EntitlementException;
import org.wso2.carbon.identity.entitlement.persistence.PersistenceManagerFactory;
import org.wso2.carbon.identity.entitlement.persistence.PolicyPersistenceManager;
import org.wso2.carbon.identity.entitlement.dto.PolicyDTO;
import org.wso2.carbon.registry.core.Resource;

public class PAPPolicyStoreManager {

    private static final Log log = LogFactory.getLog(PAPPolicyStoreManager.class);
    private PolicyPersistenceManager store;
    private PAPPolicyStoreReader storeReader;

    public PAPPolicyStoreManager() {
        store = PersistenceManagerFactory.getPolicyPersistenceManager();
        storeReader = new PAPPolicyStoreReader(store);
    }

    public void addOrUpdatePolicy(PolicyDTO policy, boolean enableVersioning) throws EntitlementException {
        store.addOrUpdatePolicy(policy, enableVersioning);
    }

    public void removePolicy(String policyId) throws EntitlementException {
        store.removePolicy(policyId);
    }

    public String[] getPolicyIds() throws EntitlementException {
        return store.listPolicyIds().toArray(new String[0]);
    }

    public PolicyDTO getPolicy(String policyId) throws EntitlementException {
        return storeReader.readPolicyDTO(policyId);
    }

    public boolean isExistPolicy(String policyId) {
        return storeReader.isExistPolicy(policyId);
    }

    public PolicyDTO getLightPolicy(String policyId) throws EntitlementException {
        return storeReader.readLightPolicyDTO(policyId);
    }

    public PolicyDTO getMetaDataPolicy(String policyId) throws EntitlementException {
        return storeReader.readMetaDataPolicyDTO(policyId);
    }

    /**
     * @param resource resource
     * @return policy
     * @throws EntitlementException throws, if fails
     * @deprecated use {@link #getPolicy(String)} instead
     */
    @Deprecated
    public PolicyDTO getPolicy(Resource resource) throws EntitlementException {
        return storeReader.readPolicyDTO(resource);
    }

    public PolicyDTO[] getAllLightPolicyDTOs() throws EntitlementException {
        return storeReader.readAllLightPolicyDTOs();
    }
}
