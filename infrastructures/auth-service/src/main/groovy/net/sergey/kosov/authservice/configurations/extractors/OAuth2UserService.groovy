package net.sergey.kosov.authservice.configurations.extractors

class OAuth2UserService {
    Map<String, String> getDetails(Map<String, Object> map) {
        def authServiceType = map.getOrDefault("_authServiceType", "ERROR")

        switch (authServiceType) {
            case "GOOGLE": return map as Map<String, String>
            case "VK": return vkConvert(map)
            case "ERROR":
                throw IllegalStateException("can not find _authServiceType")
            default: throw IllegalStateException("can not find _authServiceType")
        }
    }

    private Map<String, String> vkConvert(Map<String, Object> map) {
        def result = new HashMap<String, String>()
        def mapResult = map["response"]
        if (mapResult instanceof List) {
            mapResult.forEach { it ->
                if (it instanceof Map<Object, Object>) {
                    it.forEach { key, defue -> result[key.toString()] = defue.toString() }
                }
            }
            return result
        }
        throw IllegalStateException("can not find key 'response'")
    }

}