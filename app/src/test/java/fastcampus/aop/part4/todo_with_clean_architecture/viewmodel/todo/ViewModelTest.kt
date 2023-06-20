package fastcampus.aop.part4.todo_with_clean_architecture.viewmodel.todo

import android.app.Application
import fastcampus.aop.part4.todo_with_clean_architecture.di.appTestModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import androidx.lifecycle.LiveData
import fastcampus.aop.part4.todo_with_clean_architecture.livedata.LiveDataTestObserver
import kotlinx.coroutines.test.TestResult

@OptIn(ExperimentalCoroutinesApi::class)
internal class ViewModelTest : KoinTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var context: Application

    //-Thread 상태를 변경하기 쉽도록 하는 Dispatcher
    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        startKoin {
            androidContext(context)
            modules(appTestModule)
        }
        Dispatchers.setMain(dispatcher)
    }

    //- test후 koin, coroutine 끝내기
    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain() //- MainDispatcher를 초기화 해줘야 메모리 누수가 발생하지 X
    }

    protected fun <T> LiveData<T>.test(): LiveDataTestObserver<T> {
        val testObserver = LiveDataTestObserver<T>()
        observeForever(testObserver)

        return testObserver
    }

}