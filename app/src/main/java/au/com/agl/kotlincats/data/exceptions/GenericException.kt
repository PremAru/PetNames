package au.com.agl.kotlincats.data.model.exceptions

class GenericException : Exception(message){

    companion object {
        const val message = "An unexpected error has occurred, please contact customer support"
    }
}