/*
* Copyright 2015 herd contributors
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.finra.dm.rest;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.finra.dm.model.dto.SecurityFunctions;
import org.finra.dm.model.api.xml.BusinessObjectDataKey;
import org.finra.dm.model.api.xml.BusinessObjectDataStatusInformation;
import org.finra.dm.model.api.xml.BusinessObjectDataStatusUpdateRequest;
import org.finra.dm.model.api.xml.BusinessObjectDataStatusUpdateResponse;
import org.finra.dm.service.BusinessObjectDataService;
import org.finra.dm.ui.constants.UiConstants;

/**
 * The REST controller that handles business object data status requests.
 */
@RestController
@RequestMapping(value = UiConstants.REST_URL_BASE, produces = {"application/xml", "application/json"})
public class BusinessObjectDataStatusRestController extends DmBaseController
{
    public static final String BUSINESS_OBJECT_DATA_STATUS_URI_PREFIX = "/businessObjectDataStatus";

    @Autowired
    private BusinessObjectDataService businessObjectDataService;

    /**
     * Retrieves status information for an existing business object data.
     *
     * @param namespace the namespace
     * @param businessObjectDefinitionName the business object definition name
     * @param businessObjectFormatUsage the business object format usage
     * @param businessObjectFormatFileType the business object format file type
     * @param businessObjectFormatPartitionKey the business object format partition key.
     * @param partitionValue the partition value
     * @param subPartitionValues the list of sub-partition values
     * @param businessObjectFormatVersion the business object format version
     * @param businessObjectDataVersion the business object data version
     *
     * @return the retrieved business object data status information
     */
    @RequestMapping(
        value = BUSINESS_OBJECT_DATA_STATUS_URI_PREFIX + "/namespaces/{namespace}" +
            "/businessObjectDefinitionNames/{businessObjectDefinitionName}/businessObjectFormatUsages/{businessObjectFormatUsage}" +
            "/businessObjectFormatFileTypes/{businessObjectFormatFileType}",
        method = RequestMethod.GET)
    @Secured(SecurityFunctions.FN_BUSINESS_OBJECT_DATA_STATUS_GET)
    public BusinessObjectDataStatusInformation getBusinessObjectDataStatus(@PathVariable("namespace") String namespace,
        @PathVariable("businessObjectDefinitionName") String businessObjectDefinitionName,
        @PathVariable("businessObjectFormatUsage") String businessObjectFormatUsage,
        @PathVariable("businessObjectFormatFileType") String businessObjectFormatFileType,
        @RequestParam(value = "partitionKey", required = false) String businessObjectFormatPartitionKey, @RequestParam("partitionValue") String partitionValue,
        @RequestParam(value = "subPartitionValues", required = false) DelimitedFieldValues subPartitionValues,
        @RequestParam(value = "businessObjectFormatVersion", required = false) Integer businessObjectFormatVersion,
        @RequestParam(value = "businessObjectDataVersion", required = false) Integer businessObjectDataVersion)
    {
        return businessObjectDataService.getBusinessObjectDataStatus(
            new BusinessObjectDataKey(namespace, businessObjectDefinitionName, businessObjectFormatUsage, businessObjectFormatFileType,
                businessObjectFormatVersion, partitionValue, subPartitionValues == null ? new ArrayList<String>() : subPartitionValues.getValues(),
                businessObjectDataVersion), businessObjectFormatPartitionKey);
    }

    /**
     * Updates status of a legacy business object data without subpartition values.
     *
     * @param businessObjectDefinitionName the business object definition name
     * @param businessObjectFormatUsage the business object format usage
     * @param businessObjectFormatFileType the business object format file type
     * @param businessObjectFormatVersion the business object format version
     * @param partitionValue the partition value
     * @param businessObjectDataVersion the business object data version
     * @param request the business object data status update request
     *
     * @return the business object data status update response
     */
    @RequestMapping(
        value = BUSINESS_OBJECT_DATA_STATUS_URI_PREFIX +
            "/businessObjectDefinitionNames/{businessObjectDefinitionName}/businessObjectFormatUsages/{businessObjectFormatUsage}" +
            "/businessObjectFormatFileTypes/{businessObjectFormatFileType}/businessObjectFormatVersions/{businessObjectFormatVersion}" +
            "/partitionValues/{partitionValue}/businessObjectDataVersions/{businessObjectDataVersion}",
        method = RequestMethod.PUT)
    @Secured(SecurityFunctions.FN_BUSINESS_OBJECT_DATA_STATUS_PUT)
    public BusinessObjectDataStatusUpdateResponse updateBusinessObjectDataStatus(
        @PathVariable("businessObjectDefinitionName") String businessObjectDefinitionName,
        @PathVariable("businessObjectFormatUsage") String businessObjectFormatUsage,
        @PathVariable("businessObjectFormatFileType") String businessObjectFormatFileType,
        @PathVariable("businessObjectFormatVersion") Integer businessObjectFormatVersion, @PathVariable("partitionValue") String partitionValue,
        @PathVariable("businessObjectDataVersion") Integer businessObjectDataVersion, @RequestBody BusinessObjectDataStatusUpdateRequest request)
    {
        // Update status of the business object data.
        return businessObjectDataService.updateBusinessObjectDataStatus(
            new BusinessObjectDataKey(null, businessObjectDefinitionName, businessObjectFormatUsage, businessObjectFormatFileType, businessObjectFormatVersion,
                partitionValue, new ArrayList<String>(), businessObjectDataVersion), request);
    }

    /**
     * Updates status of a business object data without subpartition values.
     *
     * @param namespace the namespace
     * @param businessObjectDefinitionName the business object definition name
     * @param businessObjectFormatUsage the business object format usage
     * @param businessObjectFormatFileType the business object format file type
     * @param businessObjectFormatVersion the business object format version
     * @param partitionValue the partition value
     * @param businessObjectDataVersion the business object data version
     * @param request the business object data status update request
     *
     * @return the business object data status update response
     */
    @RequestMapping(
        value = BUSINESS_OBJECT_DATA_STATUS_URI_PREFIX + "/namespaces/{namespace}" +
            "/businessObjectDefinitionNames/{businessObjectDefinitionName}/businessObjectFormatUsages/{businessObjectFormatUsage}" +
            "/businessObjectFormatFileTypes/{businessObjectFormatFileType}/businessObjectFormatVersions/{businessObjectFormatVersion}" +
            "/partitionValues/{partitionValue}/businessObjectDataVersions/{businessObjectDataVersion}",
        method = RequestMethod.PUT)
    @Secured(SecurityFunctions.FN_BUSINESS_OBJECT_DATA_STATUS_PUT)
    public BusinessObjectDataStatusUpdateResponse updateBusinessObjectDataStatus(@PathVariable("namespace") String namespace,
        @PathVariable("businessObjectDefinitionName") String businessObjectDefinitionName,
        @PathVariable("businessObjectFormatUsage") String businessObjectFormatUsage,
        @PathVariable("businessObjectFormatFileType") String businessObjectFormatFileType,
        @PathVariable("businessObjectFormatVersion") Integer businessObjectFormatVersion, @PathVariable("partitionValue") String partitionValue,
        @PathVariable("businessObjectDataVersion") Integer businessObjectDataVersion, @RequestBody BusinessObjectDataStatusUpdateRequest request)
    {
        // Update status of the business object data.
        return businessObjectDataService.updateBusinessObjectDataStatus(
            new BusinessObjectDataKey(namespace, businessObjectDefinitionName, businessObjectFormatUsage, businessObjectFormatFileType,
                businessObjectFormatVersion, partitionValue, new ArrayList<String>(), businessObjectDataVersion), request);
    }

    /**
     * Updates status of a legacy business object data with 1 subpartition values.
     *
     * @param businessObjectDefinitionName the business object definition name
     * @param businessObjectFormatUsage the business object format usage
     * @param businessObjectFormatFileType the business object format file type
     * @param businessObjectFormatVersion the business object format version
     * @param partitionValue the partition value
     * @param subPartition1Value the value of the first subpartition
     * @param businessObjectDataVersion the business object data version
     * @param request the business object data status update request
     *
     * @return the business object data status update response
     */
    @RequestMapping(
        value = BUSINESS_OBJECT_DATA_STATUS_URI_PREFIX +
            "/businessObjectDefinitionNames/{businessObjectDefinitionName}/businessObjectFormatUsages/{businessObjectFormatUsage}" +
            "/businessObjectFormatFileTypes/{businessObjectFormatFileType}/businessObjectFormatVersions/{businessObjectFormatVersion}" +
            "/partitionValues/{partitionValue}/subPartition1Values/{subPartition1Value}/businessObjectDataVersions/{businessObjectDataVersion}",
        method = RequestMethod.PUT)
    @Secured(SecurityFunctions.FN_BUSINESS_OBJECT_DATA_STATUS_PUT)
    public BusinessObjectDataStatusUpdateResponse updateBusinessObjectDataStatus(
        @PathVariable("businessObjectDefinitionName") String businessObjectDefinitionName,
        @PathVariable("businessObjectFormatUsage") String businessObjectFormatUsage,
        @PathVariable("businessObjectFormatFileType") String businessObjectFormatFileType,
        @PathVariable("businessObjectFormatVersion") Integer businessObjectFormatVersion, @PathVariable("partitionValue") String partitionValue,
        @PathVariable("subPartition1Value") String subPartition1Value, @PathVariable("businessObjectDataVersion") Integer businessObjectDataVersion,
        @RequestBody BusinessObjectDataStatusUpdateRequest request)
    {
        // Update status of the business object data.
        return businessObjectDataService.updateBusinessObjectDataStatus(
            new BusinessObjectDataKey(null, businessObjectDefinitionName, businessObjectFormatUsage, businessObjectFormatFileType, businessObjectFormatVersion,
                partitionValue, Arrays.asList(subPartition1Value), businessObjectDataVersion), request);
    }

    /**
     * Updates status of a business object data with 1 subpartition values.
     *
     * @param namespace the namespace
     * @param businessObjectDefinitionName the business object definition name
     * @param businessObjectFormatUsage the business object format usage
     * @param businessObjectFormatFileType the business object format file type
     * @param businessObjectFormatVersion the business object format version
     * @param partitionValue the partition value
     * @param subPartition1Value the value of the first subpartition
     * @param businessObjectDataVersion the business object data version
     * @param request the business object data status update request
     *
     * @return the business object data status update response
     */
    @RequestMapping(
        value = BUSINESS_OBJECT_DATA_STATUS_URI_PREFIX + "/namespaces/{namespace}" +
            "/businessObjectDefinitionNames/{businessObjectDefinitionName}/businessObjectFormatUsages/{businessObjectFormatUsage}" +
            "/businessObjectFormatFileTypes/{businessObjectFormatFileType}/businessObjectFormatVersions/{businessObjectFormatVersion}" +
            "/partitionValues/{partitionValue}/subPartition1Values/{subPartition1Value}/businessObjectDataVersions/{businessObjectDataVersion}",
        method = RequestMethod.PUT)
    @Secured(SecurityFunctions.FN_BUSINESS_OBJECT_DATA_STATUS_PUT)
    public BusinessObjectDataStatusUpdateResponse updateBusinessObjectDataStatus(@PathVariable("namespace") String namespace,
        @PathVariable("businessObjectDefinitionName") String businessObjectDefinitionName,
        @PathVariable("businessObjectFormatUsage") String businessObjectFormatUsage,
        @PathVariable("businessObjectFormatFileType") String businessObjectFormatFileType,
        @PathVariable("businessObjectFormatVersion") Integer businessObjectFormatVersion, @PathVariable("partitionValue") String partitionValue,
        @PathVariable("subPartition1Value") String subPartition1Value, @PathVariable("businessObjectDataVersion") Integer businessObjectDataVersion,
        @RequestBody BusinessObjectDataStatusUpdateRequest request)
    {
        // Update status of the business object data.
        return businessObjectDataService.updateBusinessObjectDataStatus(
            new BusinessObjectDataKey(namespace, businessObjectDefinitionName, businessObjectFormatUsage, businessObjectFormatFileType,
                businessObjectFormatVersion, partitionValue, Arrays.asList(subPartition1Value), businessObjectDataVersion), request);
    }

    /**
     * Updates status of a legacy business object data with 2 subpartition values.
     *
     * @param businessObjectDefinitionName the business object definition name
     * @param businessObjectFormatUsage the business object format usage
     * @param businessObjectFormatFileType the business object format file type
     * @param businessObjectFormatVersion the business object format version
     * @param partitionValue the partition value
     * @param subPartition1Value the value of the first subpartition
     * @param subPartition2Value the value of the second subpartition
     * @param businessObjectDataVersion the business object data version
     * @param request the business object data status update request
     *
     * @return the business object data status update response
     */
    @RequestMapping(
        value = BUSINESS_OBJECT_DATA_STATUS_URI_PREFIX +
            "/businessObjectDefinitionNames/{businessObjectDefinitionName}/businessObjectFormatUsages/{businessObjectFormatUsage}" +
            "/businessObjectFormatFileTypes/{businessObjectFormatFileType}/businessObjectFormatVersions/{businessObjectFormatVersion}" +
            "/partitionValues/{partitionValue}/subPartition1Values/{subPartition1Value}/subPartition2Values/{subPartition2Value}" +
            "/businessObjectDataVersions/{businessObjectDataVersion}",
        method = RequestMethod.PUT)
    @Secured(SecurityFunctions.FN_BUSINESS_OBJECT_DATA_STATUS_PUT)
    public BusinessObjectDataStatusUpdateResponse updateBusinessObjectDataStatus(
        @PathVariable("businessObjectDefinitionName") String businessObjectDefinitionName,
        @PathVariable("businessObjectFormatUsage") String businessObjectFormatUsage,
        @PathVariable("businessObjectFormatFileType") String businessObjectFormatFileType,
        @PathVariable("businessObjectFormatVersion") Integer businessObjectFormatVersion, @PathVariable("partitionValue") String partitionValue,
        @PathVariable("subPartition1Value") String subPartition1Value, @PathVariable("subPartition2Value") String subPartition2Value,
        @PathVariable("businessObjectDataVersion") Integer businessObjectDataVersion, @RequestBody BusinessObjectDataStatusUpdateRequest request)
    {
        // Update status of the business object data.
        return businessObjectDataService.updateBusinessObjectDataStatus(
            new BusinessObjectDataKey(null, businessObjectDefinitionName, businessObjectFormatUsage, businessObjectFormatFileType, businessObjectFormatVersion,
                partitionValue, Arrays.asList(subPartition1Value, subPartition2Value), businessObjectDataVersion), request);
    }

    /**
     * Updates status of a business object data with 2 subpartition values.
     *
     * @param namespace the namespace
     * @param businessObjectDefinitionName the business object definition name
     * @param businessObjectFormatUsage the business object format usage
     * @param businessObjectFormatFileType the business object format file type
     * @param businessObjectFormatVersion the business object format version
     * @param partitionValue the partition value
     * @param subPartition1Value the value of the first subpartition
     * @param subPartition2Value the value of the second subpartition
     * @param businessObjectDataVersion the business object data version
     * @param request the business object data status update request
     *
     * @return the business object data status update response
     */
    @RequestMapping(
        value = BUSINESS_OBJECT_DATA_STATUS_URI_PREFIX + "/namespaces/{namespace}" +
            "/businessObjectDefinitionNames/{businessObjectDefinitionName}/businessObjectFormatUsages/{businessObjectFormatUsage}" +
            "/businessObjectFormatFileTypes/{businessObjectFormatFileType}/businessObjectFormatVersions/{businessObjectFormatVersion}" +
            "/partitionValues/{partitionValue}/subPartition1Values/{subPartition1Value}/subPartition2Values/{subPartition2Value}" +
            "/businessObjectDataVersions/{businessObjectDataVersion}",
        method = RequestMethod.PUT)
    @Secured(SecurityFunctions.FN_BUSINESS_OBJECT_DATA_STATUS_PUT)
    public BusinessObjectDataStatusUpdateResponse updateBusinessObjectDataStatus(@PathVariable("namespace") String namespace,
        @PathVariable("businessObjectDefinitionName") String businessObjectDefinitionName,
        @PathVariable("businessObjectFormatUsage") String businessObjectFormatUsage,
        @PathVariable("businessObjectFormatFileType") String businessObjectFormatFileType,
        @PathVariable("businessObjectFormatVersion") Integer businessObjectFormatVersion, @PathVariable("partitionValue") String partitionValue,
        @PathVariable("subPartition1Value") String subPartition1Value, @PathVariable("subPartition2Value") String subPartition2Value,
        @PathVariable("businessObjectDataVersion") Integer businessObjectDataVersion, @RequestBody BusinessObjectDataStatusUpdateRequest request)
    {
        // Update status of the business object data.
        return businessObjectDataService.updateBusinessObjectDataStatus(
            new BusinessObjectDataKey(namespace, businessObjectDefinitionName, businessObjectFormatUsage, businessObjectFormatFileType,
                businessObjectFormatVersion, partitionValue, Arrays.asList(subPartition1Value, subPartition2Value), businessObjectDataVersion), request);
    }

    /**
     * Updates status of a legacy business object data with 3 subpartition values.
     *
     * @param businessObjectDefinitionName the business object definition name
     * @param businessObjectFormatUsage the business object format usage
     * @param businessObjectFormatFileType the business object format file type
     * @param businessObjectFormatVersion the business object format version
     * @param partitionValue the partition value
     * @param subPartition1Value the value of the first subpartition
     * @param subPartition2Value the value of the second subpartition
     * @param subPartition3Value the value of the third subpartition
     * @param businessObjectDataVersion the business object data version
     * @param request the business object data status update request
     *
     * @return the business object data status update response
     */
    @RequestMapping(
        value = BUSINESS_OBJECT_DATA_STATUS_URI_PREFIX +
            "/businessObjectDefinitionNames/{businessObjectDefinitionName}/businessObjectFormatUsages/{businessObjectFormatUsage}" +
            "/businessObjectFormatFileTypes/{businessObjectFormatFileType}/businessObjectFormatVersions/{businessObjectFormatVersion}" +
            "/partitionValues/{partitionValue}/subPartition1Values/{subPartition1Value}/subPartition2Values/{subPartition2Value}" +
            "/subPartition3Values/{subPartition3Value}/businessObjectDataVersions/{businessObjectDataVersion}",
        method = RequestMethod.PUT)
    @Secured(SecurityFunctions.FN_BUSINESS_OBJECT_DATA_STATUS_PUT)
    public BusinessObjectDataStatusUpdateResponse updateBusinessObjectDataStatus(
        @PathVariable("businessObjectDefinitionName") String businessObjectDefinitionName,
        @PathVariable("businessObjectFormatUsage") String businessObjectFormatUsage,
        @PathVariable("businessObjectFormatFileType") String businessObjectFormatFileType,
        @PathVariable("businessObjectFormatVersion") Integer businessObjectFormatVersion, @PathVariable("partitionValue") String partitionValue,
        @PathVariable("subPartition1Value") String subPartition1Value, @PathVariable("subPartition2Value") String subPartition2Value,
        @PathVariable("subPartition3Value") String subPartition3Value, @PathVariable("businessObjectDataVersion") Integer businessObjectDataVersion,
        @RequestBody BusinessObjectDataStatusUpdateRequest request)
    {
        // Update status of the business object data.
        return businessObjectDataService.updateBusinessObjectDataStatus(
            new BusinessObjectDataKey(null, businessObjectDefinitionName, businessObjectFormatUsage, businessObjectFormatFileType, businessObjectFormatVersion,
                partitionValue, Arrays.asList(subPartition1Value, subPartition2Value, subPartition3Value), businessObjectDataVersion), request);
    }

    /**
     * Updates status of a business object data with 3 subpartition values.
     *
     * @param namespace the namespace
     * @param businessObjectDefinitionName the business object definition name
     * @param businessObjectFormatUsage the business object format usage
     * @param businessObjectFormatFileType the business object format file type
     * @param businessObjectFormatVersion the business object format version
     * @param partitionValue the partition value
     * @param subPartition1Value the value of the first subpartition
     * @param subPartition2Value the value of the second subpartition
     * @param subPartition3Value the value of the third subpartition
     * @param businessObjectDataVersion the business object data version
     * @param request the business object data status update request
     *
     * @return the business object data status update response
     */
    @RequestMapping(
        value = BUSINESS_OBJECT_DATA_STATUS_URI_PREFIX + "/namespaces/{namespace}" +
            "/businessObjectDefinitionNames/{businessObjectDefinitionName}/businessObjectFormatUsages/{businessObjectFormatUsage}" +
            "/businessObjectFormatFileTypes/{businessObjectFormatFileType}/businessObjectFormatVersions/{businessObjectFormatVersion}" +
            "/partitionValues/{partitionValue}/subPartition1Values/{subPartition1Value}/subPartition2Values/{subPartition2Value}" +
            "/subPartition3Values/{subPartition3Value}/businessObjectDataVersions/{businessObjectDataVersion}",
        method = RequestMethod.PUT)
    @Secured(SecurityFunctions.FN_BUSINESS_OBJECT_DATA_STATUS_PUT)
    public BusinessObjectDataStatusUpdateResponse updateBusinessObjectDataStatus(@PathVariable("namespace") String namespace,
        @PathVariable("businessObjectDefinitionName") String businessObjectDefinitionName,
        @PathVariable("businessObjectFormatUsage") String businessObjectFormatUsage,
        @PathVariable("businessObjectFormatFileType") String businessObjectFormatFileType,
        @PathVariable("businessObjectFormatVersion") Integer businessObjectFormatVersion, @PathVariable("partitionValue") String partitionValue,
        @PathVariable("subPartition1Value") String subPartition1Value, @PathVariable("subPartition2Value") String subPartition2Value,
        @PathVariable("subPartition3Value") String subPartition3Value, @PathVariable("businessObjectDataVersion") Integer businessObjectDataVersion,
        @RequestBody BusinessObjectDataStatusUpdateRequest request)
    {
        // Update status of the business object data.
        return businessObjectDataService.updateBusinessObjectDataStatus(
            new BusinessObjectDataKey(namespace, businessObjectDefinitionName, businessObjectFormatUsage, businessObjectFormatFileType,
                businessObjectFormatVersion, partitionValue, Arrays.asList(subPartition1Value, subPartition2Value, subPartition3Value),
                businessObjectDataVersion), request);
    }

    /**
     * Updates status of a legacy business object data with 4 subpartition values.
     *
     * @param businessObjectDefinitionName the business object definition name
     * @param businessObjectFormatUsage the business object format usage
     * @param businessObjectFormatFileType the business object format file type
     * @param businessObjectFormatVersion the business object format version
     * @param partitionValue the partition value
     * @param subPartition1Value the value of the first subpartition
     * @param subPartition2Value the value of the second subpartition
     * @param subPartition3Value the value of the third subpartition
     * @param subPartition4Value the value of the forth subpartition
     * @param businessObjectDataVersion the business object data version
     * @param request the business object data status update request
     *
     * @return the business object data status update response
     */
    @RequestMapping(
        value = BUSINESS_OBJECT_DATA_STATUS_URI_PREFIX +
            "/businessObjectDefinitionNames/{businessObjectDefinitionName}/businessObjectFormatUsages/{businessObjectFormatUsage}" +
            "/businessObjectFormatFileTypes/{businessObjectFormatFileType}/businessObjectFormatVersions/{businessObjectFormatVersion}" +
            "/partitionValues/{partitionValue}/subPartition1Values/{subPartition1Value}/subPartition2Values/{subPartition2Value}" +
            "/subPartition3Values/{subPartition3Value}/subPartition4Values/{subPartition4Value}/businessObjectDataVersions/{businessObjectDataVersion}",
        method = RequestMethod.PUT)
    @Secured(SecurityFunctions.FN_BUSINESS_OBJECT_DATA_STATUS_PUT)
    public BusinessObjectDataStatusUpdateResponse updateBusinessObjectDataStatus(
        @PathVariable("businessObjectDefinitionName") String businessObjectDefinitionName,
        @PathVariable("businessObjectFormatUsage") String businessObjectFormatUsage,
        @PathVariable("businessObjectFormatFileType") String businessObjectFormatFileType,
        @PathVariable("businessObjectFormatVersion") Integer businessObjectFormatVersion, @PathVariable("partitionValue") String partitionValue,
        @PathVariable("subPartition1Value") String subPartition1Value, @PathVariable("subPartition2Value") String subPartition2Value,
        @PathVariable("subPartition3Value") String subPartition3Value, @PathVariable("subPartition4Value") String subPartition4Value,
        @PathVariable("businessObjectDataVersion") Integer businessObjectDataVersion, @RequestBody BusinessObjectDataStatusUpdateRequest request)
    {
        // Update status of the business object data.
        return businessObjectDataService.updateBusinessObjectDataStatus(
            new BusinessObjectDataKey(null, businessObjectDefinitionName, businessObjectFormatUsage, businessObjectFormatFileType, businessObjectFormatVersion,
                partitionValue, Arrays.asList(subPartition1Value, subPartition2Value, subPartition3Value, subPartition4Value), businessObjectDataVersion),
            request);
    }

    /**
     * Updates status of a business object data with 4 subpartition values.
     *
     * @param namespace the namespace
     * @param businessObjectDefinitionName the business object definition name
     * @param businessObjectFormatUsage the business object format usage
     * @param businessObjectFormatFileType the business object format file type
     * @param businessObjectFormatVersion the business object format version
     * @param partitionValue the partition value
     * @param subPartition1Value the value of the first subpartition
     * @param subPartition2Value the value of the second subpartition
     * @param subPartition3Value the value of the third subpartition
     * @param subPartition4Value the value of the forth subpartition
     * @param businessObjectDataVersion the business object data version
     * @param request the business object data status update request
     *
     * @return the business object data status update response
     */
    @RequestMapping(
        value = BUSINESS_OBJECT_DATA_STATUS_URI_PREFIX + "/namespaces/{namespace}" +
            "/businessObjectDefinitionNames/{businessObjectDefinitionName}/businessObjectFormatUsages/{businessObjectFormatUsage}" +
            "/businessObjectFormatFileTypes/{businessObjectFormatFileType}/businessObjectFormatVersions/{businessObjectFormatVersion}" +
            "/partitionValues/{partitionValue}/subPartition1Values/{subPartition1Value}/subPartition2Values/{subPartition2Value}" +
            "/subPartition3Values/{subPartition3Value}/subPartition4Values/{subPartition4Value}/businessObjectDataVersions/{businessObjectDataVersion}",
        method = RequestMethod.PUT)
    @Secured(SecurityFunctions.FN_BUSINESS_OBJECT_DATA_STATUS_PUT)
    public BusinessObjectDataStatusUpdateResponse updateBusinessObjectDataStatus(@PathVariable("namespace") String namespace,
        @PathVariable("businessObjectDefinitionName") String businessObjectDefinitionName,
        @PathVariable("businessObjectFormatUsage") String businessObjectFormatUsage,
        @PathVariable("businessObjectFormatFileType") String businessObjectFormatFileType,
        @PathVariable("businessObjectFormatVersion") Integer businessObjectFormatVersion, @PathVariable("partitionValue") String partitionValue,
        @PathVariable("subPartition1Value") String subPartition1Value, @PathVariable("subPartition2Value") String subPartition2Value,
        @PathVariable("subPartition3Value") String subPartition3Value, @PathVariable("subPartition4Value") String subPartition4Value,
        @PathVariable("businessObjectDataVersion") Integer businessObjectDataVersion, @RequestBody BusinessObjectDataStatusUpdateRequest request)
    {
        // Update status of the business object data.
        return businessObjectDataService.updateBusinessObjectDataStatus(
            new BusinessObjectDataKey(namespace, businessObjectDefinitionName, businessObjectFormatUsage, businessObjectFormatFileType,
                businessObjectFormatVersion, partitionValue, Arrays.asList(subPartition1Value, subPartition2Value, subPartition3Value, subPartition4Value),
                businessObjectDataVersion), request);
    }
}
