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

package org.wso2.carbon.identity.entitlement.common.dto;

import java.util.List;

/**
 *
 */
public class RequestDTO {

    private boolean multipleRequest;

    private boolean returnPolicyIdList;

    private boolean combinedDecision;

    private List<RowDTO> rowDTOs;

    public boolean isCombinedDecision() {
        return combinedDecision;
    }

    public void setCombinedDecision(boolean combinedDecision) {
        this.combinedDecision = combinedDecision;
    }

    public List<RowDTO> getRowDTOs() {
        return rowDTOs;
    }

    public void setRowDTOs(List<RowDTO> rowDTOs) {
        this.rowDTOs = rowDTOs;
    }

    public boolean isReturnPolicyIdList() {
        return returnPolicyIdList;
    }

    public void setReturnPolicyIdList(boolean returnPolicyIdList) {
        this.returnPolicyIdList = returnPolicyIdList;
    }

    public boolean isMultipleRequest() {
        return multipleRequest;
    }

    public void setMultipleRequest(boolean multipleRequest) {
        this.multipleRequest = multipleRequest;
    }
}
