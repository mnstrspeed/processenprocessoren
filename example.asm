loop:  READ [-4], R1
       SUB -1, R1, R0
       J.Z loop
       WRITE R1, [-4]
       J loop
