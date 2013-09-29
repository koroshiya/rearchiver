ReArchiver
==========

Simply put, this utility repacks RAR/CBR archives into ZIP/CBZ archives with minimal disk IO.
Files are never extracted to disk. They are extracted to memory, then written into a ZIP archive.

Though this utility was designed with comic books in mind, it will work with any RAR/CBR archives, 
provided that they are not password-protected.
The aim of this program is to automate the process of extracting RAR/CBR archives and re-zipping them.

So, why do we want to do this? Some people despise RAR archives because of the proprietary nature,
others love the password protection they offer, and some couldn't care less. For the most part, it's personal preference.
However, the truth is that RAR archives are much slower to create and extract than ZIP archives.
They are also not as well supported as ZIP, and many operating systems can't extract RAR archives by default.

If you are archiving standard image files, such as PNG or JPG files, or other files that offer marginal compression,
then you will not benefit from using RAR archives.
In such a situation, the extra speed and compatibility of a ZIP file is highly desirable, 
particularly on mobile devices and older computers.



*Note: The utility used to extract the RAR archives (junrar) comes with its own license.
If you wish to modify this program, first make sure to read junrar's license.
