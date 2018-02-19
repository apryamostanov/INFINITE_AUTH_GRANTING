import groovy.json.JsonSlurper

String l_response_body="""{
    "_embedded": {
        "dataFields": [
            {
                "fieldName": "proxy_number",
                "fieldValue": "%PROXYNUMBER%",
                "_links": {
                    "self": {
                        "href": "https://localhost:8080/api/dataFields/17"
                    },
                    "dataField": {
                        "href": "https://localhost:8080/api/dataFields/17"
                    }
                }
            },
            {
                "fieldName": "old_access_token",
                "fieldValue": "%OLDACCESSTOKEN%",
                "_links": {
                    "self": {
                        "href": "https://localhost:8080/api/dataFields/19"
                    },
                    "dataField": {
                        "href": "https://localhost:8080/api/dataFields/19"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "https://localhost:8080/api/authentications/3/authenticationData?.publicDataFieldSet"
        }
    }
}"""

JsonSlurper l_configuration_api_response_json_slurper = new JsonSlurper()
Object l_slurped_conf_api_response_json = l_configuration_api_response_json_slurper.parseText(l_response_body)
System.out.println("z")