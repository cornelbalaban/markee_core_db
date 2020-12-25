

interface DbOperationsInterface<T> {

    fun create(withObject: T)
    fun read(forObject: T)
    fun update(forObject: T)
    fun delete(forObject: T)
    
}