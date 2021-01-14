

interface DbOperationsInterface<T,K> {

    fun create(withObject: T): T
    fun update(forObject: T): T
    fun delete(forObject: K): K
    
}