// See README.md for license details.

import chisel3._
import chiseltest._
import chisel3.testers.BasicTester

import org.scalatest.freespec.AnyFreeSpec

import freechips.rocketchip.util._


class BroadCasterSPec  extends AnyFreeSpec with ChiselScalatestTester {
  "BroadCaster test" in {
    test(new Broadcaster(UInt(32.W), 2)) { dut =>
      dut.io.in.bits.poke(123.U)
      dut.io.in.valid.poke(1.U)
      dut.io.out(0).ready.poke(1.U)
      dut.io.out(1).ready.poke(1.U)
      dut.io.out(0).valid.expect(1.U)
      dut.io.out(1).valid.expect(0.U)
      dut.io.out(0).bits.expect(123.U)
      dut.clock.step()
      dut.io.in.bits.poke(456.U)
      dut.io.out(1).valid.expect(1.U)
      dut.io.out(0).valid.expect(0.U)
      dut.io.out(1).bits.expect(123.U)
      dut.clock.step()
      dut.io.out(0).bits.expect(456.U)
      dut.io.out(0).valid.expect(1.U)

      dut.io.in.bits.poke(789.U)

      dut.clock.step()
      dut.io.out(0).valid.expect(0.U)
      dut.io.out(1).valid.expect(1.U)
      dut.io.out(0).ready.poke(0.U)

      dut.clock.step()
      dut.io.out(1).ready.poke(0.U)
      dut.io.out(0).valid.expect(1.U)
      dut.clock.step()
      dut.io.out(0).valid.expect(1.U)
      dut.io.out(1).valid.expect(0.U)
      dut.io.out(0).ready.poke(1.U)
      dut.clock.step()
      dut.io.out(0).valid.expect(0.U)
      dut.io.out(0).bits.expect(789.U)
      dut.io.out(1).valid.expect(1.U)
      dut.io.out(1).bits.expect(789.U)
      dut.clock.step()
      dut.io.out(0).valid.expect(0.U)
      dut.io.out(0).bits.expect(789.U)
      dut.io.out(1).valid.expect(1.U)
      dut.io.out(1).bits.expect(789.U)
    }
  }
}
