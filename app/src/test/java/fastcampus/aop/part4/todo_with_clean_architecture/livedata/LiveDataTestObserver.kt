package fastcampus.aop.part4.todo_with_clean_architecture.livedata

import androidx.lifecycle.Observer

/**
 * 사용법
 * 값이하나하나 바뀔때마다 내부에서 하나씩 값을 들고있는 상태를 보여주게됨
 * 나중에 추후에 기대하는 expectation값과 바뀌게된 특정 instance의 순서를 비교하기위해서 구성
 */
class LiveDataTestObserver<T> : Observer<T> {

    private val values: MutableList<T> = mutableListOf()

    override fun onChanged(t: T) {
        values.add(t)
    }

    fun assertValueSequence(sequence: List<T>): LiveDataTestObserver<T> {
        var i = 0
        val actualIterator = values.iterator()
        val expectedIterator = sequence.iterator()

        var actualNext: Boolean
        var expectedNext: Boolean

        while (true) {
            actualNext = actualIterator.hasNext()
            expectedNext = expectedIterator.hasNext()


            if (!actualNext || !expectedNext) break

            val actual: T = actualIterator.next()
            val expected: T = expectedIterator.next()

            if (actual != expected) {
                throw AssertionError("actual: ${actual}, expected: ${expected}, index: $i")
            }

            i++
        }

        if (actualNext) {
            throw AssertionError("More values received than expected ($i)")
        }
        if (expectedNext) {
            throw AssertionError("Fewer values received than expected ($i)")
        }

        return this
    }
}