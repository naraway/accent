# accent

- [Summary](#summary)
    * [Module overview](#module-overview)
- [Models](#models)
    * [DDD](#ddd)
    * [Event Message](#event-message)
    * [Command Request](#command-request)
    * [Query Request](#query-request)
    * [Client Request](#client-request)

---

## Summary

`accent` constructs the base model in naraway and
Prescribes relationships between models of types. In addition, it has a shared model, Jpo (RDBMS), and Doc (Mongo) store model, and sets keys for each Workspace and User level.


### Module overview

The dependency relationship between metro multi-tenency service libraries is configured as follows.

![](http://www.plantuml.com/plantuml/png/RP11ZzD038NlyokivGA7b5q4d3WKIYljPGyK2QJIW-Dn6xEJSUIPh9JzzKmILaeGf_6pdr_yPPioMkxRu1hn32H6WUUwvToeJywbHuaT6hP00LFwdBD9tMU-9ur7UMQuNLY2HCv3nz1f64RnlvbQYLuCTcFk5pNFjjEW1dL0UbmOJFleIIsomGVdJAvDXejgBdgdtJ0tNjCRUDZ__FvjluVxI9jVSPia4iExsxlt6_YuWRlRZNEaRQUHOmOa6f-Jcuk_JtKnBQAgAbV1Lxv8b2mQGI_mW8Rm0mV0420rQEnX94h5kUjJrbQUSJBN0z3KO8FEz3TJ1aaGDKEdAKaTkEmS2GD3ZQdaQ0I9Nfx4zrZ29NE2t1JoQrC63PjaB33kl2JmXYsM_A3NdWl8fAkNnZzo1NQShf-99ji40XWzO9-R4YuqeRmTfTeBM4aIMXznC--tGgQ9xKc8qmsyEXmEpftcVtg6JrK5fzMvPxVMIyywh7njoz7tRVW3)
<!--
```plantuml
hide circle
hide methods

skinparam classAttributeIconSize 0
skinparam linetype polyline
skinparam linetype ortho
skinparam ClassBorderColor black
skinparam roundcorner 5

right footer Copyright (c) NEXTREE Inc.\n@since 2014. 6. 10.

component accent [
accent
accent-front
---
- Definition of Nara Way all shared types
- Customization by customer project is not possible
- Scale based on individual sites through inheritance
]

component dramaprologue [
drama-prologue
drama-prologue-front
---
- Shared Nara drama type and authentication/authorization logic
]

component "drama microservices" #FFF

[accent] <-- [dramaprologue]
[dramaprologue] <-- [drama microservices]
```
-->

## Models

### DDD

It defines the basic model for domain-based development. It consists of an entity base model and annotations for automatic generation.

![](http://www.plantuml.com/plantuml/png/VPFVJnin3CVVxw-8j1SGQWNJjWU44AnKY0q1eWXDOZpaSkxL8ublZg_IiV6_h_U3BdUql3d-VkpOZdCIHBEKpYOBp40PP6EXjHt8WhAG9E49VQ5PEsMi3k5KX34j1IuC-Ha-WzgF48iUf2f05MIh-h198rwbZeH9dVShSGOy8KkiKglDKmGmbJupn1vOVNvJaVgJA9Mk8lA6Qr8S7O4l7R0M97zyh719N6t2q0lmN1jOGOu4bqt49b9xJ_AQps6GYYd0HZ8Dmjh8YjD5OT6qEL-IX35VY9eJ2R2QK56rZXspgwwcF-vkfrDruStufpy9w0seZ_i7dyRgorWTx8-JfEtsZ9n6Vvhd3BaMKBqUUipK2qgbueAQMIarKfWTgfam-hnpG0FV0mSaVwWihHM678Dqixn31ptLKOPpD9l4lLQiTgwqWtjjIxZ48BkDzg7JrkiKXjHBryktG7u6ZDhYiqxjeE6keJqbL8Cxkt5RZVk6GQJgY-laCz4vl3SdXjybrDr_X-fGdHgXsbh7D_MUCvN5iAmUSPt-0YFRaFR-Ivp3f38Mr71vsrfMVLXqw7MpoAgk1PVGc6V4Jdjfx5jmj8HiNU4v-3eJN1Trjg3YHOxwB9WAO448zPHki_IcTLGFZnrH5fcM-W7-Zt6bTTV44TkkyDf5iPtJa2sTlzektLN5rgZIsjU2KY8BklbaRnVcxwWSzHxYNQPvpet4gFUnalZGoF4w9SdmeuuU5fXbu1-pGPxX4yLWDDSaicDck3_92VYiTFOV)
<!--
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
-->

### Event Message

The relationship between event-based messages inherited from trail messages is as follows.

![](http://www.plantuml.com/plantuml/png/bLDFRnC_4Btlfx1w-lqEYLe47Agg0j8SAX4acXoGW8DZJv9HRSyodWGMw7V7YRE9arqgSVEyz-RDpfzTeGLba8s3QlVs82koiQhY8uPQi_RAE1tZMn76-LhWtb2OuazGbuN4OG1fQb0rkMORz774oReWHblVTyGMU4IEMCsTDe-5W6aTh24EmEfLvulKhqgfUSfOxdGxYviR26iFh0KftDugt10tVJ8C0hpG1fB8a-1cbz6dr26Gj7uUepHC4NgLyoYiZIIThck79di-LHNZSYLgGIJ0QaHrau7_pF_gm_ZJx64yLlV13B-4Dn630VNYykhbKBqUgglBOLNbRcUiqKqWHhq4LHRRpK6fWK9xhQR269PxGBOfzs51rshMFlUKpqQpj9SzEJaYrLDHioJK-NnpwP532F80tzOG9IFalGwsX3wkWPiIk6k2zcYwk1Ozta3OH-GrXXmNdsLWsqogNtPQz5yssS4Zy0ODzAhFqLep19bqd4fsclt-cPlqLbn7u4wXv_ULDPo7E4EVD8uEkTyrId8gy7QGnN3FJkgWQ9VHVtAsfVE7tPLXrV5uveGUO8bHWC7kmWbPN60ACkTf0_RqAaSK17x8yG9M3Dg-9wDT6j0nE6HD9M4-z_BNFwRMZIDjSpTzp6ZhrK-TeO8UecXV7_2dgZhvYGSyk5KNnMOlkfizJN3R16clFydRvwdkvlV03ihbzj35RtPuxGkSBFNYRAdb60VsymgjXV0rfPLR-GC0)
<!--
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
-->

### Command Request

The relationship between data handling (CUD) based messages inherited from domain messages is as follows.

![](http://www.plantuml.com/plantuml/png/bLDDRzD04BtxLunoWaEY5W67geeAQIf58fL8WyI1osOzIKVT3pCx3fZI_uwTTLDx7LNYvdtlpPkTsUUh88gbjEQUSWHDh0rcWs-BSk_pa6NXWLoXM5dGHeNmIOHfKmekj7Ttz0VXh2Cnv52g0g7mfceEfpZFjNM7c3M-dptdo3DlFCF6AFtG4R0lNQuzEsJuCBWHF6O0cxfYTz0TB2ulqPKMMGbvDvq2xJrNfsJa17chDDOYwuNsXufJIkMSbsS_Iq4gzW5FAZT1M6cfTQee3Ede-PHbJBjxWQttWWmpNrGHUAFVmkty-terdyF2wSaFTnN8QOHtP-Vl9_1n0kTdaooBqwvPaLbY26g7q6lMx05W39HVm9qmkLqBI5EoS5j_4QkRpvQoqMXT5TYIonSaUuez1mIa-usjPuRGoGf_bXWa8jvQvV8kzBL4hhh0TUMK9Jt4bQZv7brxybQHY-VEjJGsmy1ZRz9q_dR80HwGzwJnf3f52yNePDbpIjcxwFVVtF9anp8W3ngz_bvHmt69QxArnlXZxIz5KZjryDxxhZ0KtWNQ68J-Z_7OwfBdF9q-7JNnXcr1ztAnomlNjZs6BSMFnkY4f5hauGA-K93Bk9TfV-WRHGRhfMsyDwXSIsvhj6GyxlccTquxlvBm5Uue2FBXiFGvRQiMjtuVH_mMaAznImxpet6lNsUYtngBnlzMsVJrZrmIZfIU-Vo5Uu8iwuO2nZoUmgWJcj4WD4c1QGha-STgvErdbZp-k1p_dVRSKaNqwkHXrCj3eevNVOx7Q12Fv1pxfyaTog9jFm2ZD01NwFBIcdy0)
<!--
```
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
-->

### Query Request

The relationship between inquiry(R)-based messages inherited from domain messages is as follows.

![](http://www.plantuml.com/plantuml/png/bLHDRzD04BtxLunoWaEY5W67g8fQGYf5eWNQ73XmsQmdwQZxuSwE0wRqlsDd7UDDB25EsNdpvitkp7CkWoYMqfe7oX4qiJQOdPmjoeFFGvQ5HtA5OcL16nN2bGZJkXHSQk_kwHV2MOzYoA5K1KBXJTK4GpdFjNGlCMzqFtZEaUVUU8QrKVgnHs1VkbnxTiZmxkH6y9m1hEkAxPwtbxYuG5TQP2NatMm6jFDS3T782V96QQn9rWljzXL3JEMSbuEUfI0L-u23p7KGLbfgdYeAGpfglcGPq_P1OEEz8CFS5rK4NkdNSBlujhfRB63fzEIxkmpaDCARi_Et4tW_WVEpIPR5rwvOaRd14DGM8MdMp05W39HFuLwOtBO5f2bPkesVmkfmR5Cs2gsg0gSzwOXaBsdFBW79VBlMSqFev0wVIWmI4M-jSdaV-beYLttWO-MK9NsAAr6B7ResybQHYt7lMXgRny1pyjBq_TqZJ_20l2EDm-mZj52CJcwEbPBiVTJxxzpDOCSo8AUD_hcloE4un1NPccDyL_j3aTHAFJpPxns6mhj0QuEGVXZF1zvLQxRzheRDnr45fuT5xwCswJURW3A5p_lVhdr_zKUgBMN89CFzdnh65yNAXdY5DZXk5c_PbQ4JacgPrqMVACX57FeidUYr8eFv5DRU6rIkJMvgj6JibdYTn5tV1Eur_gcn4FBkLjdK0RrSfpsSdllyR-u5iYpvZnZpU0QZdd56fyP92qnJ88UFQq-FnomrmBWS_vubOYcXRNsmn2YT_J7NHB7KYSUal15C9puwddXYadZ7H2LBpTJnJecNwFBIcZy0)
<!--
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
-->

### Client Request

The relationship between Client messages inherited from domain messages is as follows.

![](http://www.plantuml.com/plantuml/png/bL9BZn914BxthwZiIG-GNQCUDXk28WSEcBYGwC5BqrD0PVinMrs36NN_kmtDc8QPc7YR_kfxr6EcKJHBuwnIUweG3B6ncByToZvKKQduHBxMh1qOgsFy8CAqQGGN9lWL_KIuBIYMF4fR8zJ1jiV7K2rmiYuAiwFlny0LyYpOmB2nsZmL10wDhqnWZmplUXt1BmMmIOhTYNUoU7X0tpXaBHJyP09q2DmEqSWBybORJ2GNX0udnH1JUn-aytCKfUKGSP2vYSBQIEBfkhPaikUBKaoxlS0sX1GAir2t6NXbNiFd-RVrutmE2s_6t_qqaZS8RsxltexX_HZkRiTAvMdNhCakCKQzGoZ3Jdi0605Lzx1AL_AxCo17oS9lmpsikyzpoMMZTLlZVM6T4VMICti5kDvlZfvPGY-F-DnWb8m4vxIlIkXBWzoMmAVMQqUcZslHymFwyoiuJJw_YxOC7eT9-UMavVp5a3qy8X_8u23x6gqrf-XbuTIlhhBVVzUMWubDQgyNzEzxPGxd9Qx99OuDVxK_D4boAl1kaTtXckUak_gfEivNt5pIKaML7S28HnEuAJgywNLu9R17WNHNJFJkKrr6ZPhHxmcKMfNRwN4kC3L5NpNE_W40)

<!--
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
-->