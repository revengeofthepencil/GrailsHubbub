import grails.test.mixin.*
import spock.lang.Specification

import com.grailsinaction.PostController

@TestFor(UrlMappings)
@Mock(PostController)
class UrlMappingsSpec extends Specification {
	/*
	def "Ensure basic mapping operations for user permalink"() {
		expect:
		assertForwardUrlMapping(url, controller: expectCtl, action: expectAction) {
			id = expectId
		}
		
		where:
		url	|	expectCtl	|	expectAction	|	expectId
		'/users/glen'|	'post'|	'timeline'	|	'glen'
		'/timeline/chuck_norris'|	'post'|	'timeline'	|	'chuck_norris'
	}
	*/
	
	
	def "Ensure basic mapping operations for user permalink"() {

		expect:
		assertForwardUrlMapping(url, controller: expectCtrl, action: expectAction) {
			id = expectId
		}

		where:
		url		            | expectCtrl| expectAction  | expectId
		'/users/glen'           | 'post'    | 'timeline'    | 'glen'
		'/timeline/chuck_norris'| 'post'    | 'timeline'    | 'chuck_norris'
	}
	
}