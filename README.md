# EBS
**EBS**  - Element Based Serialization is a simple serialization format I made to use for my personal projects.
## Reading/Writing
Reading and Writing to a file or stream with EBS is easy, you can use ``EBS.write(EbsElement, OutputStream)`` to write to an output stream or ``EBS.writeFile(EbsElement, File)`` to write to a file.
To read an element use ``EBS.read(InputStream)`` or ``EBS.readFile(File)``
## Compounds
Most data in EBS will be stored in something called a compound, you can create it with ``EbsCompound.create()`` or ``EbsCompound.create(int)`` if you want the compound to start with a specific size.
  
The EbsCompound is split into 3 different interfaces, [CompoundGetter](src/main/java/me/julie/ebs/element/CompoundGetter.java) which hosts getter methods such as ``EbsElement.get(String)``; [CompoundSetter](src/main/java/me/julie/ebs/element/CompoundSetter.java) which has methods relating to adding elements into the compound and finally [EbsCompound](src/main/java/me/julie/ebs/element/EbsCompound.java) itself which implements ``EbsElement``.
  
For more info and javadocs check out the above hyper links