package au.com.agl.kotlincats.presentation.adapter

data class HeaderDataItem(var headerText: String) : DataItem() {
    override val id = headerText.hashCode().toLong()
}
