

interface DbOperationsInterface<T,K,R> {

    fun create(withObject: T): R
    fun update(forObject: T): R
    fun delete(forObject: K): K
    
}