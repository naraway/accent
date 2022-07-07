# accent

[toc]

---

## Summary

`accent` constructs the base model in naraway and
Prescribes relationships between models of types. In addition, it has a shared model, Jpo (RDBMS), and Doc (Mongo) store model, and sets keys for each Workspace and User level.
configure.


### Module overview

The dependency relationship between metro multi-tenency service libraries is configured as follows.

```plantuml
skinparam componentStyle rectangle
skinparam ComponentBorderColor black

component accent [
accent
accent-front
---
- Definition of nara All Shared Types
- Customization by customer project is not possible
- Scale based on individual sites through inheritance
]

component dramaprologue [
drama-prologue
drama-prologue-front
---
- Shared drama type and authentication/authorization logic
]

component "drama microservices" #FFF

[accent] <-- [dramaprologue]
[dramaprologue] <-- [drama microservices]
```

## Ubiquitous language model

### DDD

It defines the basic model for domain-based development. It consists of an entity base model and annotations for automatic generation.

```plantuml
hide circle
hide methods

skinparam classAttributeIconSize 0
skinparam linetype polyline
skinparam linetype ortho
skinparam ClassBorderColor black
skinparam roundcorner 5

skinparam class {
  backgroundColor<<enumeration>> ivory
  backgroundColor<<interface>> motivation
  backgroundColor<<annotation>> mistyrose
  backgroundColor<<abstract>> application
}

right footer Copyright (c) NEXTREE Inc.\n@since 2014. 6. 10.

class DomainAggregate <<interface>>
class DomainEntity <<abstract>> {
    - id: String
    - entityVersion: long
    - registerationTime: long
    - modificationTime: long
    + modify(NameValueList)
    # modifyAttributes(NameValueList)
}
class JsonSerializable <<interface>> {
    + toJson(): String
    + toPrettyJson(): String
}
class StageEntity <<abstract>> {
    - requesterKey: ActorKey
}
class ValueGroup <<interface>> {}
class ValueObject <<interface>> {}
class EntityLifeCycle <<enumeration>> {
    Preliminary
    Active
    Dormant
    Removed
}
class GenerateOptions <<annotation>> {
    - properties(): RdbProperty[]
    - updatable(): String[]
}
class RdbProperty <<annotation>> {
    - name(): String
    - columnName(): String
    - columnType(): String
    - columnNullable(): boolean
}

JsonSerializable <|-u- ValueObject 
JsonSerializable <|-u- ValueGroup
JsonSerializable <|-- DomainEntity
DomainEntity <|-- StageEntity

DomainAggregate -[hidden]d- DomainEntity
GenerateOptions -[hidden]d- RdbProperty
RdbProperty -[hidden]d- EntityLifeCycle
```

### Event Message

The relationship between event-based messages inherited from trail messages is as follows.

```plantuml
hide circle
hide methods

skinparam classAttributeIconSize 0
skinparam linetype polyline
skinparam linetype ortho
skinparam ClassBorderColor black
skinparam roundcorner 5

skinparam class {
  backgroundColor<<enumeration>> ivory
  backgroundColor<<interface>> motivation
  backgroundColor<<annotation>> mistyrose
  backgroundColor<<abstract>> application
}

right footer Copyright (c) NEXTREE Inc.\n@since 2014. 6. 10.

class TrailMessage <<abstract>> {
    - id: String
    - trailInfo: TrailInfo
    - messageType: TrailMessageType
}
class TrailMessageType <<enumeration>> {
    ClientRequest
    CommandRequest
    QueryRequest
    DynamicQueryRequest
    DataEvent
    DomainEvent
}
class TraceInfo {
    - trailId: String
    - messageId: String
    - service: String
    - message: String
    - parentMessageId: String
    - parentService: String
    - parentService: String
    - parentMessage: String
    - userId: String
    - messageType: TrailMessageType
    - requestTime: long
    - waitingTime: long
}
class DomainEvent <<abstract>> {}
class DataEvent <<abstract>> {
    - dataEventType: DataEventType
}
class DataEventType <<enumeration>> {
    Registered
    Modified
    Removed
}
class TrailContext {
    - threadLocal: ThreadLocal
}
class StreamEvent {
    - id: String
    - payloadType: String
    - payloadClass: String
    - payload: String
    - timestamp: String
}

TrailMessage -r-> "messageType" TrailMessageType
TrailMessage -l-> "traceInfo" TraceInfo
TrailMessage <|-d- DomainEvent
TrailMessage <|-d- DataEvent
DataEvent -l-> "dataEventType" DataEventType
StreamEvent -d[hidden]-> TrailContext
```

### Command Request

The relationship between data handling (CUD) based messages inherited from domain messages is as follows.

```plantuml
hide circle
hide methods

skinparam classAttributeIconSize 0
skinparam linetype polyline
skinparam linetype ortho
skinparam ClassBorderColor black
skinparam roundcorner 5

skinparam class {
  backgroundColor<<enumeration>> ivory
  backgroundColor<<interface>> motivation
  backgroundColor<<annotation>> mistyrose
  backgroundColor<<abstract>> application
}

right footer Copyright (c) NEXTREE Inc.\n@since 2014. 6. 10.

class TrailMessage <<abstract>> {
    - id: String
    - trailInfo: TrailInfo
    - messageType: TrailMessageType
}
class TrailMessageType <<enumeration>> {
    ClientRequest
    CommandRequest
    QueryRequest
    DynamicQueryRequest
    DataEvent
    DomainEvent
}
class TraceInfo {
    - trailId: String
    - messageId: String
    - service: String
    - message: String
    - parentMessageId: String
    - parentService: String
    - parentService: String
    - parentMessage: String
    - userId: String
    - messageType: TrailMessageType
    - requestTime: long
    - waitingTime: long
}
class TrailResponsible <<interface>> {}
class CommandRequest <<abstract>> {
    - commandType: CommandType
    - response: CommandResponse
}
class CommandResponse {
    - entityIds: List<String>
    - requestFailed: boolean
    - failureMessage: FailureMessage
}
class CommandType <<enumeration>> {
    Register
    Modify
    Remove
    UserDefined
}
class FailureMessage {
    - exceptionName: String
    - exceptionMessage: String
    - exceptionCode: String
} 

TrailMessage -r-> "messageType" TrailMessageType
TrailMessage -l-> "traceInfo" TraceInfo
CommandRequest -u-|> TrailMessage
CommandRequest -l-> "commandType" CommandType
CommandRequest -r-> "response" CommandResponse
CommandResponse -u-|> TrailResponsible
CommandResponse -r-> "failureMessage" FailureMessage
```

### Query Request

The relationship between inquiry(R)-based messages inherited from domain messages is as follows.

```plantuml
hide circle
hide methods

skinparam classAttributeIconSize 0
skinparam linetype polyline
skinparam linetype ortho
skinparam ClassBorderColor black
skinparam roundcorner 5

skinparam class {
  backgroundColor<<enumeration>> ivory
  backgroundColor<<interface>> motivation
  backgroundColor<<annotation>> mistyrose
  backgroundColor<<abstract>> application
}

right footer Copyright (c) NEXTREE Inc.\n@since 2014. 6. 10.

class TrailMessage <<abstract>> {
    - id: String
    - trailInfo: TrailInfo
    - messageType: TrailMessageType
}
class TrailMessageType <<enumeration>> {
    ClientRequest
    CommandRequest
    QueryRequest
    DynamicQueryRequest
    DataEvent
    DomainEvent
}
class TraceInfo {
    - trailId: String
    - messageId: String
    - service: String
    - message: String
    - parentMessageId: String
    - parentService: String
    - parentService: String
    - parentMessage: String
    - userId: String
    - messageType: TrailMessageType
    - requestTime: long
    - waitingTime: long
}
class TrailResponsible <<interface>> {}
class AbstractQuery <<abstract>> {
    - response: QueryReponse
    - offset: Offset
}
class QueryRequest <<abstract>> {}
class DynamicQueryRequest <<abstract>> {
    - queryParams: QueryParams
}
class QueryResponse {
    - entityIds: List<String>
    - requestFailed: boolean
    - failureMessage: FailureMessage
}
class FailureMessage {
    - exceptionName: String
    - exceptionMessage: String
    - exceptionCode: String
} 

TrailMessage -r-> "messageType" TrailMessageType
TrailMessage -l-> "traceInfo" TraceInfo
AbstractQuery -u-|> TrailMessage
AbstractQuery -r-> "response" QueryResponse
AbstractQuery -l-|> TrailResponsible
AbstractQuery -d-> "failureMessage" FailureMessage
QueryRequest -u-|> AbstractQuery
DynamicQueryRequest -u--|> AbstractQuery
```

### Client Request

The relationship between Client messages inherited from domain messages is as follows.

```plantuml
hide circle
hide methods

skinparam classAttributeIconSize 0
skinparam linetype polyline
skinparam linetype ortho
skinparam ClassBorderColor black
skinparam roundcorner 5

skinparam class {
  backgroundColor<<enumeration>> ivory
  backgroundColor<<interface>> motivation
  backgroundColor<<annotation>> mistyrose
  backgroundColor<<abstract>> application
}

right footer Copyright (c) NEXTREE Inc.\n@since 2014. 6. 10.

class TrailMessage <<abstract>> {
    - id: String
    - trailInfo: TrailInfo
    - messageType: TrailMessageType
}
class TrailMessageType <<enumeration>> {
    ClientRequest
    CommandRequest
    QueryRequest
    DynamicQueryRequest
    DataEvent
    DomainEvent
}
class TraceInfo {
    - trailId: String
    - messageId: String
    - service: String
    - message: String
    - parentMessageId: String
    - parentService: String
    - parentService: String
    - parentMessage: String
    - userId: String
    - messageType: TrailMessageType
    - requestTime: long
    - waitingTime: long
}
class ClientReuqest <<abstract>> {}
class WebClientReuqest {}

TrailMessage -r-> "messageType" TrailMessageType
TrailMessage -l-> "traceInfo" TraceInfo
ClientReuqest -u-|> TrailMessage
WebClientReuqest -u-|> ClientReuqest
```
