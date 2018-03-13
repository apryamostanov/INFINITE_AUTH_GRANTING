package validation_modules

System.out.println(this.getClass().getSimpleName())

def i_query_parameters = binding.getVariable("i_query_parameters")
def i_method = binding.getVariable("i_method")
def i_body = binding.getVariable("i_body")
def i_jwt = binding.getVariable("i_jwt")
def i_context = binding.getVariable("i_context")
def i_url_path = binding.getVariable("i_url_path")