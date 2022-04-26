# WINK-back

[![build](https://github.com/artrayme/WINK-back/actions/workflows/build.yml/badge.svg)](https://github.com/artrayme/WINK-back/actions/workflows/build.yml)

Back part of the WINK project. 

<p align="center">
<img src="docs/logo.png" alt="drawing" width="400"/>
</p>

### Overview
WINK-back is a SC-code analyzing engine and connector to sc-machine that works with any SC-like language since all of them can be converted to SC-json which goes to the analyzing engine and JMantic library after being transformed.

Implements identifier autocompletion. In order to that at the first launch WINK-back starts to read all the identifiers that are connected to sc-machine base and builds a prefix tree. 

Implements saving and deleting of SC-json in sc-machine with help of JMantic library. It works by creating nodes and edges in the base and by saving the addresses of created elements. After loading all saved addresses can be deleted thus not filling the base with extra data.
- Works in WINK-back engine mode and in offline one as a simple editor. 
- Converts SCg (supports the old and the new formats) to SCs.
- SCs syntax backlighting.
- Сonverts SCs to SC-json code for sending knowledge fragments to WINK-back.
- Displays semantic environment of loaded SC-json in editor window in SCg format. 
&ensp;
****

### Launch Instruction: 
1. Install all packets with help of ```npm install``` command: 
2. Launch the server using ```npm run start. ```
3. In different console window launch electron using ```npm run electron``` command.
