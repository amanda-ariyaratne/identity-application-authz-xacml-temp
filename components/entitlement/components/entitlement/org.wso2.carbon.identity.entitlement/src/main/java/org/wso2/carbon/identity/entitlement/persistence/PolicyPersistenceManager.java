/*
 * Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.entitlement.persistence;

import org.wso2.carbon.identity.entitlement.EntitlementException;
import org.wso2.carbon.identity.entitlement.dto.PolicyDTO;
import org.wso2.carbon.identity.entitlement.dto.PolicyStoreDTO;
import org.wso2.carbon.identity.entitlement.policy.store.PolicyStoreManageModule;

import java.util.List;

/**
 * This interface supports the management of XACML policies.
 */
public interface PolicyPersistenceManager extends PolicyStoreManageModule {

    /**
     * Adds or updates the given policy.
     *
     * @param policy      policy
     * @param isFromPapAction true if the operation originated from a PAP action, false if it is from a PDP action.
     * @throws EntitlementException If an error occurs
     */
    void addOrUpdatePolicy(PolicyDTO policy, boolean isFromPapAction) throws EntitlementException;

    /**
     * Gets the requested policy.
     *
     * @param policyId policy ID
     * @return policyDTO
     * @throws EntitlementException If an error occurs
     */
    PolicyDTO getPAPPolicy(String policyId) throws EntitlementException;

    /**
     * Gets the requested policy list.
     *
     * @param policyIds policy ID list
     * @return policyDTO
     * @throws EntitlementException If an error occurs
     */
    List<PolicyDTO> getPAPPolicies(List<String> policyIds) throws EntitlementException;

    /**
     * Gets the requested policy version.
     *
     * @param policyId policy ID
     * @param version  policy version
     * @return requested policy
     * @throws EntitlementException If an error occurs
     */
    PolicyDTO getPolicy(String policyId, String version) throws EntitlementException;

    /**
     * Gets all versions of the given policy ID.
     *
     * @param policyId policy ID
     * @return array of policy versions
     */
    String[] getVersions(String policyId);

    /**
     * Lists all PAP policy IDs.
     *
     * @return list of policy IDs
     * @throws EntitlementException If an error occurs
     */
    List<String> listPolicyIds() throws EntitlementException;

    /**
     * Removes the given policy.
     *
     * @param policyId policy ID
     * @throws EntitlementException If an error occurs
     */
    void removePolicy(String policyId) throws EntitlementException;

    /**
     * Gets the requested published policy.
     *
     * @param policyId policy ID
     * @return requested policy
     */
    PolicyStoreDTO getPublishedPolicy(String policyId);

    /**
     * Lists all published policy IDs.
     *
     * @return list of published policy IDs
     * @throws EntitlementException If an error occurs
     */
    List<String> listPublishedPolicyIds() throws EntitlementException;
}
