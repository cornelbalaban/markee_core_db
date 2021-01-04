

interface DbOperationsInterface<T,K> {

    fun create(withObject: T): T
    fun update(forObject: T)
    fun delete(forObject: K)
    
}