package de.euriale.dataspace

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DataspaceApplication

fun main(args: Array<String>) {
	//runApplication<DataspaceApplication>(*args)

	testChainOfResponsibility()

}

fun testChainOfResponsibility() {

	println("tester")

	val processor = PositiveProcessor(
		NegativeProcessor(
			ZeroProcessor(Processor.NONE)
		)
	)
	(-4..4).map {
		processor.process(Request(it))
	}


}

typealias RequestNumber = Int

@JvmInline
value class Request(val number:RequestNumber)

sealed interface Processor {
	fun process(request:Request)

	object NONE : Processor {
		override fun process(request: Request) {}
	}
}

class PositiveProcessor(
	private val processor: Processor
) : Processor {
	override fun process(request: Request) {
		if(request.number > 0) {
			println("number: ${request.number}")
		} else {
			processor.process(request)
		}
	}
}

class NegativeProcessor(
	private val processor: Processor
) : Processor {
	override fun process(request: Request) {
		if(request.number < 0) {
			println("number: ${request.number}")
		} else {
			processor.process(request)
		}
	}
}

class ZeroProcessor(
	private val processor: Processor
) : Processor {
	override fun process(request: Request) {
		if(request.number == 0) {
			println("number: ${request.number}")
		} else {
			processor.process(request)
		}
	}
}











