

interface DbOperationsInterface<T,K> {

    fun create(withObject: T, andKey: K): K
    fun update(forObject: T)
    fun delete(forObject: K)
    
}