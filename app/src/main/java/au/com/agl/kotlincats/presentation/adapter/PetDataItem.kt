package au.com.agl.kotlincats.presentation.adapter

data class PetDataItem(val petName: String) : DataItem() {
    override val id = petName.hashCode().toLong()
}