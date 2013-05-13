/**
    Copyright 2013 Richard Warburton <richard.warburton@gmail.com>

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.   
   */

package org.adoptajsr.java8.benchmarks;

import com.google.caliper.Param;
import com.google.caliper.SimpleBenchmark;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.Streams;

public class CharacterEncodings extends SimpleBenchmark {

    @Param({"10", "100", "1000", "10000", "100000"})
    int size;
    
    List<Charset> charsets;
    List<String> strings;

    @Override
    protected void setUp() throws Exception {
        charsets = new ArrayList<>(Charset.availableCharsets().values());
        strings = Stream.generate(() -> UUID.randomUUID().toString())
                        .limit(size)
                        .collect(Collectors.toList());
    }

    public List<ByteBuffer> timeImperativeEncodings(int reps) {
        List<ByteBuffer> values = null;
        for (int i = 0; i < reps; i++) {
            values = new ArrayList<>();
            for (String str : strings) {
                for (Charset charset : charsets) {
                    values.add(charset.encode(str));
                }
            }
        }
        return values;
    }

    public List<ByteBuffer> timeLambdaEncodings(int reps) {
        List<ByteBuffer> values = null;
        for (int i = 0; i < reps; i++) {
            values = strings.parallelStream()
                          .flatMap(string ->
                              charsets.stream().map(charset ->
                                  charset.encode(string)))
                          .collect(Collectors.toList());
        }
        return values;
    }

}
