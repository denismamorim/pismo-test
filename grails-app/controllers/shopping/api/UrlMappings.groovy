package shopping.api

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
		// Products
		get "/products"(controller:"product", action:"retrieve")
		get "/products/$id"(controller:"product", action:"retrieveSingle")
		post "/products"(controller:"product", action:"create")
		put "/products/$id"(controller:"product", action:"update")
		delete "/products/$id"(controller:"product", "action":"delete")
		
		// Sales
		get "/sales"(controller:"sale", action:"retrieve")
		post "/sales"(controller:"sale", action:"create")
		
    }
}
