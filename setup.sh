#!/bin/bash
#
# Copyright (c) 2024, WSO2 LLC. (http://www.wso2.com).
#
# WSO2 LLC. licenses this file to you under the Apache License,
# Version 2.0 (the "License"); you may not use this file except
# in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
#

# Variables (update these paths as necessary)
XACML_CONNECTOR="<XACML_connector_artifacts_folder>"
IS_HOME="<IS_pack_location>"

# Ensure required paths exist
if [ ! -d "$XACML_CONNECTOR" ] || [ ! -d "$IS_HOME" ]; then
  echo "Error: Ensure XACML_CONNECTOR and IS_HOME paths are correct."
  exit 1
fi

# Step 1: Copy jar files to dropins folder
echo "Copying jar files to dropins folder..."
mkdir -p "$IS_HOME/repository/components/dropins"
cp "$XACML_CONNECTOR/dropins/"*.jar "$IS_HOME/repository/components/dropins/" || {
  echo "Error copying jar files to dropins folder."
  exit 1
}

# Step 2: Copy jar file to API server lib folder
echo "Copying jar file to API server lib folder..."
mkdir -p "$IS_HOME/repository/deployment/server/webapps/api/WEB-INF/lib"
cp "$XACML_CONNECTOR/api-server/"*.jar "$IS_HOME/repository/deployment/server/webapps/api/WEB-INF/lib/" || {
  echo "Error copying jar file to API server lib folder."
  exit 1
}

# Step 3: Copy entitlement.properties
echo "Copying entitlement.properties..."
mkdir -p "$IS_HOME/repository/conf/identity"
cp "$XACML_CONNECTOR/config-files/entitlement.properties" "$IS_HOME/repository/conf/identity/" || {
  echo "Error copying entitlement.properties."
  exit 1
}

# Step 4: Copy entitlement.properties.j2
echo "Copying entitlement.properties.j2..."
mkdir -p "$IS_HOME/repository/resources/conf/templates/repository/conf/identity"
cp "$XACML_CONNECTOR/config-files/entitlement.properties.j2" "$IS_HOME/repository/resources/conf/templates/repository/conf/identity/" || {
  echo "Error copying entitlement.properties.j2."
  exit 1
}

# Step 5: Copy balana-config.xml
echo "Copying balana-config.xml..."
mkdir -p "$IS_HOME/repository/conf/security"
cp "$XACML_CONNECTOR/config-files/balana-config.xml" "$IS_HOME/repository/conf/security/" || {
  echo "Error copying balana-config.xml."
  exit 1
}

# Step 6: Append JSON content to default.json
DEFAULT_JSON="$IS_HOME/repository/resources/conf/default.json"
XACML_JSON="$XACML_CONNECTOR/config-files/org.wso2.carbon.identity.xacml.server.feature.default.json"

if [ -f "$DEFAULT_JSON" ] && [ -f "$XACML_JSON" ]; then
  echo "Appending JSON content to default.json..."
  jq -s '.[0] * .[1]' "$DEFAULT_JSON" "$XACML_JSON" > "$DEFAULT_JSON.tmp" && mv "$DEFAULT_JSON.tmp" "$DEFAULT_JSON" || {
    echo "Error appending JSON content to default.json."
    exit 1
  }
else
  echo "Error: default.json or org.wso2.carbon.identity.xacml.server.feature.default.json not found."
  exit 1
fi

# Step 7: Add default XACML policies.
echo "Copying XACML policies..."
POLICIES_DIR="$IS_HOME/repository/resources/identity/policies/xacml/default"
echo "Ensuring XACML policies directory exists..."
mkdir -p "$POLICIES_DIR"
echo "Copying XACML policies..."
cp -r "$XACML_CONNECTOR/policies/"* "$POLICIES_DIR/"

# Step 8: Append custom event listener configuration to `deployment.toml`.
DEPLOYMENT_TOML="$IS_HOME/repository/conf/deployment.toml"
echo "Appending XACML authorization handler configuration to deployment.toml..."
cat <<EOL >> "$DEPLOYMENT_TOML"

[[event_listener]]
id = "xacml_authorization_handler"
type = "org.wso2.carbon.identity.core.handler.AbstractIdentityHandler"
name = "org.wso2.carbon.identity.application.authz.xacml.handler.impl.XACMLBasedAuthorizationHandler"
order = 899
EOL

echo "Script completed successfully."
