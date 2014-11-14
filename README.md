TaxRecordWriter
===============

This is a binary writer that writes "Tax Records" in Binary. The purpose of this assignment is to write "whole" data in binary to blocks sizes in file. A text file on disk has a block size of 4096, our goal is to write a whole record inside a block block. Multiple records can fit in a single block, what we have to do is when we reach a point where a record will start in one block and end in another we need to add "dummy" data to push that record so it writes to the next block. This assignment is followed up by TaxRecordReader, where we need to read in binary and translate it to text. For now I have about 5 or 6 test records. Our professor is going to give us hundredes of records to write to disk to see if we are doing it properly. 
