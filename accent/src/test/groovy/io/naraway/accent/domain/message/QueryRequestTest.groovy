/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.message

import io.naraway.accent.domain.message.query.FooQuery
import io.naraway.accent.domain.type.Offset
import org.springframework.data.domain.*
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class QueryRequestTest extends Specification {
    //
    @Subject(AbstractQuery)
    @Shared
    FooQuery query
    Page page
    Slice slice
    List<String> content

    void setup() {
        query = new FooQuery('foo')
        query.offset = Offset.newDefault()

        content = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10']

        def pageable = new PageRequest(0, 10, Sort.by("id"))
        page = new PageImpl(content, pageable, 15)
        slice = new SliceImpl(content, pageable, true)
    }

    def 'set response using page'() {
        when:
        query.setResponse(page)

        then:
        query.getResponse().queryResult == content
        query.getResponse().offset.hasPrevious() == false
        query.getResponse().offset.hasNext() == true
        query.getResponse().offset.totalCount == 15
    }

    def 'set response using slice'() {
        when:
        query.setResponse(slice)

        then:
        query.getResponse().queryResult == content
        query.getResponse().offset.hasPrevious() == false
        query.getResponse().offset.hasNext() == true
        query.getResponse().offset.totalCount == 0 // not initialized
    }

    def 'set response using others'() {
        when:
        query.setResponse(content)

        then:
        query.getResponse().queryResult == content
    }

    def 'set failure message'() {
        given:
        def failureMessage = new FailureMessage(new Exception('Failed'))

        when:
        query.setResponse(failureMessage)

        then:
        query.getResponse().getFailureMessage() == failureMessage
    }

    def 'set query response'() {
        given:
        def queryResponse = new QueryResponse<List<String>>(content)

        when:
        query.setResponse(queryResponse)

        then:
        query.getResponse() == queryResponse
    }
}
