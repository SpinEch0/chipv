package chipv.top

import chipv.gcd.DecoupledGcd
import chipv.gcd.GcdConfig

import org.chipsalliance.cde.config._ 
import chisel3._

object topGcd extends App {
  val params = (new GcdConfig).alter((site, here, up) => {case width => 1024})
  emitVerilog (new DecoupledGcd()(params), Array("--target-dir", "generated"))   
}

