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
package org.finra.dm.service;

import org.finra.dm.model.api.xml.ExpectedPartitionValueInformation;
import org.finra.dm.model.api.xml.ExpectedPartitionValueKey;
import org.finra.dm.model.api.xml.ExpectedPartitionValuesCreateRequest;
import org.finra.dm.model.api.xml.ExpectedPartitionValuesDeleteRequest;
import org.finra.dm.model.api.xml.ExpectedPartitionValuesInformation;
import org.finra.dm.model.api.xml.PartitionKeyGroupKey;
import org.finra.dm.model.api.xml.PartitionValueRange;

/**
 * The expected partition value service.
 */
public interface ExpectedPartitionValueService
{
    public ExpectedPartitionValuesInformation createExpectedPartitionValues(ExpectedPartitionValuesCreateRequest expectedPartitionValuesCreateRequest);

    public ExpectedPartitionValueInformation getExpectedPartitionValue(ExpectedPartitionValueKey expectedPartitionValueKey, Integer offset);

    public ExpectedPartitionValuesInformation getExpectedPartitionValues(PartitionKeyGroupKey partitionKeyGroupKey, PartitionValueRange partitionValueRange);

    public ExpectedPartitionValuesInformation deleteExpectedPartitionValues(ExpectedPartitionValuesDeleteRequest expectedPartitionValuesCreateRequest);
}
