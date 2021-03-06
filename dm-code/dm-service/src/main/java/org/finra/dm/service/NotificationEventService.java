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

import java.util.List;
import java.util.concurrent.Future;

import org.finra.dm.model.jpa.NotificationEventTypeEntity;
import org.finra.dm.model.api.xml.BusinessObjectDataKey;

public interface NotificationEventService
{
    public Future<Void> processBusinessObjectDataNotificationEventAsync(NotificationEventTypeEntity.EVENT_TYPES_BDATA eventType, BusinessObjectDataKey key);

    public List<Object> processBusinessObjectDataNotificationEventSync(NotificationEventTypeEntity.EVENT_TYPES_BDATA eventType, BusinessObjectDataKey key);
}
