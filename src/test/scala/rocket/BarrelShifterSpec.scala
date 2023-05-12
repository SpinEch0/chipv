// See README.md for license details.

import chisel3._
import chiseltest._
import chisel3.testers.BasicTester

import org.scalatest.freespec.AnyFreeSpec

import freechips.rocketchip.util._

class BarrelShifterModule extends Module {
    val io = IO(new Bundle {
        val in = Input(UInt(32.W))
        val shamt = Input(UInt(2.W))
        val op = Input(UInt(2.W))
        val out = Output(UInt(32.W))
    })

    val bytes = VecInit(io.in.asBools.grouped(8).map(VecInit(_).asUInt).toSeq)
    val register = Reg(UInt(32.W))
    io.out := register
    when (io.op === 0.U) {
        register := BarrelShifter.leftShift(bytes, io.shamt).asUInt
    }.elsewhen (io.op === 1.U) {
        register := BarrelShifter.rightShift(bytes, io.shamt).asUInt
    }.elsewhen (io.op === 2.U) {
        register := BarrelShifter.leftRotate(bytes, io.shamt).asUInt
    }.otherwise {
        register := BarrelShifter.rightRotate(bytes, io.shamt).asUInt
    }
}


class BarrelShifterSpec  extends AnyFreeSpec with ChiselScalatestTester {
  "barrel shifter" in {
    test(new BarrelShifterModule()) { dut =>
      dut.io.in.poke(0.U)
      dut.io.shamt.poke(1.U)
      dut.io.op.poke(2.U)
      dut.clock.step()
      dut.io.out.expect(0.U)
      dut.io.in.poke("xAABBCCDD".U)
      dut.clock.step()
      dut.io.out.expect("xDDAABBCC".U)
      dut.io.in.poke("x8a9b0c1d".U)
      dut.clock.step()
      dut.io.out.expect("x1d8a9b0c".U)

      dut.io.op.poke(3.U)
      dut.clock.step()
      dut.io.out.expect("x9b0c1d8a".U)

      dut.io.in.poke("x8a9b0c1d".U)
      dut.io.op.poke(0.U)
      dut.clock.step()
      dut.io.out.expect("x8a9b0c".U)

      dut.io.in.poke("x8a9b0c1d".U)
      dut.io.op.poke(1.U)
      dut.io.out.expect("x8a9b0c".U)
    }
  }
}
